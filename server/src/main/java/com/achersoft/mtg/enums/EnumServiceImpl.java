package com.achersoft.mtg.enums;

import com.achersoft.mtg.enums.dao.EnumAPI;
import com.achersoft.mtg.enums.persistence.EnumMapper;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class EnumServiceImpl implements EnumService {

    private @Inject EnumMapper mapper;

    @Override
    public List<EnumAPI> getSets() {
        return mapper.getSets();
    }

    @Override
    public List<EnumAPI> getLanguages() {
        List<EnumAPI> languages = new ArrayList();
        languages.add(EnumAPI.builder().id("English").value("English").build());
        languages.add(EnumAPI.builder().id("Russian").value("Russian").build());
        languages.add(EnumAPI.builder().id("Japanese").value("Japanese").build());
        languages.add(EnumAPI.builder().id("Korean").value("Korean").build());
        languages.add(EnumAPI.builder().id("Spanish").value("Spanish").build());
        languages.add(EnumAPI.builder().id("German").value("German").build());
        languages.add(EnumAPI.builder().id("Chinese").value("Chinese").build());
        languages.add(EnumAPI.builder().id("Portuguese").value("Portuguese").build());
        languages.add(EnumAPI.builder().id("French").value("French").build());
        languages.add(EnumAPI.builder().id("Italian").value("Italian").build());
        return languages;
    }
    

}