package com.bat.system.dao.globalsetting.dataobject;

public class ShopSettingDO {

    private String key;

    private String value;

    private String desc;

    public ShopSettingDO() {}

    public ShopSettingDO(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc == null ? null : desc.trim();
    }

    @Override
    public String toString() {
        return "ShopSettingDO{" + "key='" + key + '\'' + ", value='" + value + '\'' + ", desc='" + desc + '\'' + '}';
    }
}