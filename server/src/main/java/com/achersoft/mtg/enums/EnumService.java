package com.achersoft.mtg.enums;

import com.achersoft.mtg.enums.dao.EnumAPI;
import java.util.List;

public interface EnumService {
    public List<EnumAPI> getSets();
    public List<EnumAPI> getLanguages();
}