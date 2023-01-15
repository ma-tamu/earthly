package jp.co.project.planets.earthly.webapp.controller.form;

/**
 * dto convertible
 * 
 * @param <T>
 */
public interface DtoConvertible<T> {

    /**
     * convert to dto
     * 
     * @return DTO
     */
    T toDto();
}
