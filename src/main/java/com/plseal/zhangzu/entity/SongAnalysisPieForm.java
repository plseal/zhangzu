package com.plseal.zhangzu.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongAnalysisPieForm {
    private String radio_type;
    public SongAnalysisPieForm(){
        this.radio_type = "支出";
    }
    
}
