package prototype.blacklist.entity;

public interface Validator<T> {

	boolean isValid(T object);
}
