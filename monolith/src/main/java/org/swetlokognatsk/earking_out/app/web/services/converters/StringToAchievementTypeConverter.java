package org.swetlokognatsk.earking_out.app.web.services.converters;

import org.springframework.core.convert.converter.Converter;
import org.swetlokognatsk.earking_out.core.domain.model.achievements.AchievementTypes;
import org.swetlokognatsk.earking_out.infrastructure.annotations.WebComponent;

@WebComponent
public class StringToAchievementTypeConverter implements Converter<String, AchievementTypes> {

    public AchievementTypes convert(final String source) {
        return AchievementTypes.valueOf(source.toUpperCase());
    }

}
