package net.southplus.southplusmaid.model.dlsite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonSetter;
import net.southplus.southplusmaid.config.Config;

import java.util.List;
import java.util.Map;
public class DLSiteWork {
    private String workno;
    private String work_name;
    private String maker_id;
    private String maker_name;
    private DLImage image_main;
    private DLImage image_thum;
    private DLImage image_thum_mini;
    private String image_thumb;
    private DLCreater creaters;
    private List<DLGenre> genres;
    @JsonIgnore
    private List<DLLanguage> language_editions;

    private String age_category;
    private String age_category_string;
    private String regist_date;

    private String type;


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * @description: 判断年龄
     * @name: isNsfw
     * @author: Leck
     * @param:
     * @return  java.lang.Boolean
     * @date:   2024/1/14
     */
    public Boolean isNsfw(){
        if (age_category.equals("1")){
            return false;
        }else {
            return true;
        }
    }

    public String getWorkno() {
        return workno;
    }

    public void setWorkno(String workno) {
        this.workno = workno;
    }

    public String getWork_name() {
        return work_name;
    }

    public void setWork_name(String work_name) {
        this.work_name = work_name;
    }

    public String getMaker_id() {
        return maker_id;
    }

    public void setMaker_id(String maker_id) {
        this.maker_id = maker_id;
    }

    public String getMaker_name() {
        return maker_name;
    }

    public void setMaker_name(String maker_name) {
        this.maker_name = maker_name;
    }

    public DLImage getImage_main() {
        return image_main;
    }

    public void setImage_main(DLImage image_main) {
        this.image_main = image_main;
    }

    public DLImage getImage_thum() {
        return image_thum;
    }

    public void setImage_thum(DLImage image_thum) {
        this.image_thum = image_thum;
    }

    public DLImage getImage_thum_mini() {
        return image_thum_mini;
    }

    public void setImage_thum_mini(DLImage image_thum_mini) {
        this.image_thum_mini = image_thum_mini;
    }

    public DLCreater getCreaters() {
        return creaters;
    }

    public void setCreaters(DLCreater creaters) {
        this.creaters = creaters;
    }

    public List<DLGenre> getGenres() {
        return genres;
    }

    public void setGenres(List<DLGenre> genres) {
        this.genres = genres;
    }

    public List<DLLanguage> getLanguage_editions() {
        return language_editions;
    }

    public void setLanguage_editions(List<DLLanguage> language_editions) {
        this.language_editions = language_editions;
    }


    public String getAge_category() {
        return age_category;
    }

    public void setAge_category(String age_category) {
        this.age_category = age_category;
    }

    public String getAge_category_string() {
        return age_category_string;
    }

    public void setAge_category_string(String age_category_string) {
        this.age_category_string = age_category_string;
    }

    public String getRegist_date() {
        int i = regist_date.indexOf(" ");
        if (i !=-1){
            return regist_date.substring(0,i);
        }else {
            return regist_date;
        }

    }

    public void setRegist_date(String regist_date) {
        this.regist_date = regist_date;
    }

    public String getImage_thumb() {
        return DLImage.HEAD+image_thumb;
    }

    public void setImage_thumb(String image_thumb) {
        this.image_thumb = image_thumb;
    }
}
