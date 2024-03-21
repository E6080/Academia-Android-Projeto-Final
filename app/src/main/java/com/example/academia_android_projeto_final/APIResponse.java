package com.example.academia_android_projeto_final;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class APIResponse
{
    @SerializedName("count")
    public int count;

    @SerializedName("next")
    public String next;

    @SerializedName("previous")
    public String previous;

    @SerializedName("results")
    public List<ResultData> resultDataList = null;



    public static class ResultData
    {
        @SerializedName("name")
        public String name;
        @SerializedName("url")
        public String url;
    }
}

