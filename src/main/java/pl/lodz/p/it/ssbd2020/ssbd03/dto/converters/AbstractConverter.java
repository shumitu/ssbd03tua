package pl.lodz.p.it.ssbd2020.ssbd03.dto.converters;

import pl.lodz.p.it.ssbd2020.ssbd03.dto.ConvertableDTO;
import pl.lodz.p.it.ssbd2020.ssbd03.exceptions.AppException;

public abstract class AbstractConverter<A, B extends ConvertableDTO> {
    public abstract A convert(B dto);

    public abstract B convert(A entity) throws AppException;
}
