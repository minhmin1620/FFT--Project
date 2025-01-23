package dataaccess;
//Contributing authors: Minh nguyen
public interface TransactionRepo<T> {
	T load(String filePath);
}
