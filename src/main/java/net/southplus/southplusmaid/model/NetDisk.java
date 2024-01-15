package net.southplus.southplusmaid.model;

/**
 * @program: SouthPlusMaid
 * @description:
 * @author: Leck
 * @create: 2024-01-14 03:17
 */
public class NetDisk {
    private String name;
    private String[] keywords;

    public NetDisk() {
    }

    public NetDisk(String name,String...  keywords) {
        this.name = name;
        this.keywords=keywords;
    }


    public Boolean hasKeyWords(String row){
        String lowerCase = row.toLowerCase();
        for (String keyword : this.keywords) {
            if (lowerCase.contains(keyword))
                return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getKeywords() {
        return keywords;
    }

    public void setKeywords(String[] keyword) {
        this.keywords = keyword;
    }


}