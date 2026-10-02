package org.swetlokognatsk.earking_out.app.web;

import java.util.Map;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.servlet.HandlerMapping;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.Exercise;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseNames;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExerciseTypes;
import org.swetlokognatsk.earking_out.core.domain.model.exercises.ExercisesFactory;

// TODO put it in some more specific package
// TODO test
@Component
public class ExerciseArgumentResolver implements HandlerMethodArgumentResolver {

    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType()
                .equals(Exercise.class);
    }

    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        var pathParamsMap = (Map<String, String>) webRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE, RequestAttributes.SCOPE_REQUEST);

        var exerciseName = pathParamsMap.get("exerciseName");
        var exerciseType = pathParamsMap.get("exerciseType");

        return mapExercisePathParamsToExercise(exerciseName, exerciseType);
    }

    private Exercise mapExercisePathParamsToExercise(final String exerciseNameSlug, final String exerciseTypeSlug) {
        var exerciseName = toUpperAndTryToFindAmong(exerciseNameSlug, ExerciseNames.values());
        if (exerciseName == null) {
            return null;
        }

        var exerciseType = toUpperAndTryToFindAmong(exerciseTypeSlug, ExerciseTypes.values());
        if (exerciseType == null) {
            return null;
        }

        return ExercisesFactory.create(exerciseName, exerciseType);

    }

    private <T> T toUpperAndTryToFindAmong(final String slug, final T[] enumValues) {
        var enumItemName = slugToUpperSnake(slug);
        T enumItem = null;
        for (var possibleEnumItem : enumValues) {
            if (possibleEnumItem.toString().equals(enumItemName)) {
                enumItem = possibleEnumItem;
                break;
            }
        }
        return enumItem;
    }

    /**
     * perfect-pitch => PERFECT_PITCH
     */
    private static String slugToUpperSnake(final String slug) {
        return slug.replace('-', '_')
                .toUpperCase();
    }
}
