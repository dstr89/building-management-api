package dev.wsad.bm.core.exceptions;

import org.apache.commons.lang3.StringUtils;

public class BuildingNotFoundException extends RuntimeException {

    public static final String ERROR_MSG =  "Could not find building";

    public BuildingNotFoundException(Long id) {
        super(StringUtils.join(ERROR_MSG, StringUtils.SPACE, id));
    }

}
