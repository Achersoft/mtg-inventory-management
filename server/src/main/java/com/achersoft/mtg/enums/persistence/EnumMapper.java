package com.achersoft.mtg.enums.persistence;

import com.achersoft.mtg.enums.dao.EnumAPI;
import java.util.List;

public interface EnumMapper {
    public List<EnumAPI> getSets();
}