package com.resgain.base.abs;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mvel2.PropertyAccessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import com.resgain.base.abs.bean.QueryPage;
import com.resgain.base.abs.bean.ResultPage;
import com.resgain.base.abs.bean.SqlParse;
import com.resgain.dragon.exception.KnowException;
import com.resgain.dragon.filter.ActionContext;
import com.resgain.dragon.util.SqlConfig;

/**
 * 业务服务基础方法类
 * @author memphis
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class AbstractService
{
	@Autowired private SessionFactory sessionFactory;

	@Autowired
	protected JdbcTemplate jdbcTemplate;

	//获取session
	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	//取得当前系统日期，主要是保证在多个服务器之间获取的日期是一致的-暂时采用new Date
	protected Date getToday() {
		return new Date();
	}

	//获取指定的实体信息
	protected <T extends AbstractObject> T get(Class<T> clzz, final String id) {
		if (isNullOrSpace(id))
			return null;
		return (T) getSession().get(clzz, id);
	}

	/**
	 * 根据form表单需要填的属性保存
	 * @param bm
	 * @param prefix
	 * @param expect
	 */
	@SuppressWarnings("null")
	public <T extends AbstractObject> T smartSave(T po, String... expect) {

		String prefix = null;

		if (prefix == null && expect.length == 0)
			return save(po);

		HttpServletRequest request = ActionContext.getHttpServletRequest();
		Enumeration<?> enu = request.getParameterNames();
		List<String> expects = expect == null ? new ArrayList<String>() : Arrays.asList(expect);
		List<String> props = new ArrayList<String>();
		if (!prefix.endsWith("."))
			prefix = prefix + ".";
		while (enu.hasMoreElements()) {
			String key = enu.nextElement().toString();
			if (key.startsWith(prefix) && !expects.contains(key)) {
				props.add(key.substring(prefix.length()));
			}
		}
		if (po instanceof IDictObject) {
			IDictObject dict = (IDictObject) po;
			AbstractObject d = getExistedDictData(po.getClass(), dict.getCode(), po.getOtherString_());
			if (d != null && !d.getId().equals(po.getId())) {
				if (d.isDelFlag()) {
					// if (isNullOrSpace(po.getId())) { // 如果是新增,则把老记录恢复
					// po.setId(d.getId());
					// props.add("delFlag");
					// }
					throw new KnowException("存在已经删除的代码[" + dict.getCode() + "]，不能新增，如果必须，请联系系统管理员恢复该删除数据! ");
				} else
					throw new KnowException("代码[" + dict.getCode() + "]重复");
			}
			if (d != null)
				getSession().evict(d);
		}
		return save(po, props.toArray(new String[0]));
	}

	/**
	 * 保存新增或修改的对象.
	 */
	protected <T extends AbstractObject> T save(final T po, String... props) {
		if (isNullOrSpace(po.getId())) {
			po.setId(null);
		}
		if (po.getId() != null && props != null && props.length > 0) // 如果是更新且限制更新的属性
		{
			T bm = (T) get(po.getClass(), po.getId());
			getSession().evict(bm);
			try {
				for (String prop : props) {
					if (!isNullOrSpace(prop)) {
						PropertyAccessor.set(bm, prop, PropertyAccessor.get(prop, po));
					}
				}
				getSession().update(bm);
			} catch (Exception e) {
				throw new RuntimeException(e.getLocalizedMessage());
			}
			return bm;
		} else { // 直接保存
			getSession().saveOrUpdate(po);
			return po;
		}
	}

	//删除实体信息(逻辑删除).
	protected <T extends AbstractObject> void delete(Class<T> clzz, final String id) {
		get(clzz, id).setDelFlag(true);
	}

	//根据查询HQL与参数列表创建Query对象.
	protected Query query(String hql, Object... values) {
		Query query = getSession().createQuery(hql);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	//获取指定条件的唯一对象
	protected <T extends AbstractObject> T get(Class<T> cls, String join, String where, Object... values) {
		return (T) query(getHql(cls, join, where), values).uniqueResult();
	}

	//获取指定条件的数据对象列表
	protected <T extends AbstractObject> List<T> find(Class<T> cls, String join, String where, Object... values) {
		List<T> ret = (List<T>) query(getHql(cls, join, where), values).list();
		if (!isNullOrSpace(join))
			return new ArrayList(new HashSet<T>(ret));
		return ret;
	}

	//取得指定条件的分页数据对象列表
	protected <T extends AbstractObject> ResultPage<T> findPage(Class<T> cls, QueryPage qp, String join, String where, Object... params) {
		if (qp == null)
			qp = new QueryPage();
        String hql = getHql(cls, join, where);
        String tmp = hql;
		String order = qp.getOrder_();
		if (!isNullOrSpace(order) && order.indexOf('.') < 0)
			order = "a." + order.trim();
		if (!isNullOrSpace(order) && tmp.toUpperCase().indexOf(" ORDER ") > 0 && tmp.toUpperCase().indexOf(" BY ") > 0)
			tmp = tmp.substring(0, tmp.toUpperCase().indexOf(" ORDER ")) + " order by " + order;
		else if (!isNullOrSpace(order))
			tmp = tmp + " order by " + order;
		int count = getCount(getHql(cls, null, where), params);
        List<T> list = new ArrayList<T>();
        if(count>0)
        {
            Query query = query(tmp, params);
            if(qp.getPage()>0)
                query.setFirstResult((int)((qp.getPage() - 1) * qp.getLimit()));
            else {
                query.setFirstResult((int)qp.getStart());
                qp.setPage((int)(qp.getStart()/qp.getLimit()+1));
            }
            query.setMaxResults((int)qp.getLimit());
            list = query.list();
        }
        return new ResultPage(list, count, qp.getPage(), qp.getLimit());
    }

	protected <T extends AbstractObject> int update(Class<T> cls, String set, String where, Object... params) {
		if (set == null)
			return 0;
		String hql = "update " + cls.getName() + " set " + set + " ";
		if (where != null)
			hql += "where " + where;
		Query query = getSession().createQuery(hql);
		if (where != null && params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object p = params[i];
				query.setParameter(i, p);
			}
		}
		return query.executeUpdate();
	}

	protected <T extends AbstractObject> int delete(Class<T> cls, String where, Object... params) {
		String hql = "delete from " + cls.getName() + " where " + where;
		Query query = getSession().createQuery(hql);
		if (where != null && params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				Object p = params[i];
				query.setParameter(i, p);
			}
		}
		return query.executeUpdate();
	}

	protected <T extends AbstractObject> String getHql(Class<T> cls, String join, String where) {
		return str("from ", cls.getName(), " a", getJoinString(join), " where a.delFlag=false", isNullOrSpace(where) ? "" : " and " + where);
	}

	private <T extends AbstractObject> T getExistedDictData(Class<T> cls, String code, String otherCond) {
		List<T> t = query(str("from ", cls.getName(), " where code=?", isNullOrSpace(otherCond) ? "" : otherCond), code).list();
		if (t.size() == 0)
			return null;
		else if (t.size() == 1) {
			return t.get(0);
		} else {
			T ret = null;
			for (T t2 : t) {
				ret = t2;
				if (!t2.isDelFlag())
					return t2;
			}
			return ret;
		}
	}

    //取得指定HQL语句的结果记录数量
    private int getCount(final String hql, final Object... params) {
        return ((Long)query(getCountSQL(hql), params).list().get(0)).intValue();
    }

    private String getCountSQL(final String hql)
    {
        String tmp = "select count(*) " + hql.substring(hql.toLowerCase().indexOf("from "), hql.length());
        if (tmp.toLowerCase().lastIndexOf(" order ") > 0)
            tmp = tmp.substring(0, tmp.toLowerCase().lastIndexOf(" order "));
        return tmp;
    }

	private String getJoinString(String str) {
		if (isNullOrSpace(str))
			return "";
		StringBuffer ret = new StringBuffer();
		for (String t : str.split(",")) {
			ret.append(" left join fetch a.").append(t.trim());
		}
		return ret.toString();
	}

	private static String str(String... parts) {
		return StringUtils.join(parts);
	}

	protected List<Map<String, Object>> queryForList(String sql, Object... params) {
		return jdbcTemplate.queryForList(sql, params);
	}

	protected Map<String, Object> queryForMap(String sql, Object... params) {
		List<Map<String, Object>> tmp = queryForList(sql, params);
		if (tmp == null || tmp.isEmpty())
			return null;
		return tmp.get(0);
	}

	protected long count(String sql, Object... args) {
		Number number = jdbcTemplate.queryForObject(sql, args, Long.class);
		return (number != null ? number.longValue() : 0);
	}

	protected ResultPage<Map<String, Object>> fetchPage(final String countSql, final String sql, final Object args[], final int page, final int pageSize) {
		final long rowCount = count(countSql, args);
		final ResultPage<Map<String, Object>> ret = new ResultPage<Map<String, Object>>(new ArrayList<Map<String, Object>>(), rowCount, page, pageSize);
		final int startRow = (page - 1) * pageSize;
		final int endRow = startRow + pageSize;
		jdbcTemplate.query(sql, args, new ResultSetExtractor<List<Map<String, Object>>>() {
			@Override
			public List<Map<String, Object>> extractData(ResultSet rs) throws SQLException, DataAccessException {
				int currentRow = 0;
				ResultSetMetaData rsmd = rs.getMetaData();
				while (rs.next() && currentRow < endRow) { // FIXME 先循环丢弃取法针对特大型数据会有性能问题
					if (currentRow >= startRow) {
						Map<String, Object> map = new HashMap<String, Object>();
						for (int col = 1; col <= rsmd.getColumnCount(); col++) {
							int type = rsmd.getColumnType(col);
							if (type == java.sql.Types.DATE || type == java.sql.Types.TIME || type == java.sql.Types.TIMESTAMP)
								map.put(rsmd.getColumnLabel(col).toLowerCase(), rs.getObject(col));
							else
								map.put(rsmd.getColumnLabel(col).toLowerCase(), rs.getString(col));
						}
						ret.getResultList().add(map);
					}
					currentRow++;
				}
				return ret.getResultList();
			}
		});
		return ret;
	}

	protected static boolean isNullOrSpace(String... str) {
		if (str == null)
			return true;
		for (String s : str) {
			if (StringUtils.isBlank(s))
				return true;
		}
		return false;
	}

	protected static SqlParse getSql(String key) {
		return new SqlParse(SqlConfig.getSql(key));
	}
}