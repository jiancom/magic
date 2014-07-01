package com.resgain.base.abs.bean;

public class Crumb
{
    private String id;
    private String url;
    private String title;
    
    public Crumb(String id, String url, String title) {
        super();
        this.id = id;
        this.url = url;
        this.title = title;
    }

    public String getId()
    {
        return id;
    }

    public String getUrl()
    {
        return url;
    }

    public String getTitle()
    {
        return title;
    }

    @Override
    public boolean equals(Object obj)
    {
        if(obj!=null && obj instanceof Crumb)
            return this.id.equals(((Crumb)obj).getId());
        return super.equals(obj);
    }
}

