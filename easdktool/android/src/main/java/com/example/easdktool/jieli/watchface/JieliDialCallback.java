package com.example.easdktool.jieli.watchface;


import com.jieli.jl_rcsp.model.base.BaseError;

import java.util.List;

public interface JieliDialCallback {
    void jieliDial(List<JieliWatchInfo> watchInfos);
    void error(BaseError error);
}
