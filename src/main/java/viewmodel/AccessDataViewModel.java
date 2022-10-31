package viewmodel;



import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AccessDataViewModel {

	private final StringProperty userName = new SimpleStringProperty();
	private final StringProperty userMajor = new SimpleStringProperty();
        private final int age=0;
	private final ReadOnlyBooleanWrapper writePossible = new ReadOnlyBooleanWrapper();

	public AccessDataViewModel() {
		writePossible.bind(userName.isNotEmpty().and(userMajor.isNotEmpty()));
	}

	public StringProperty userNameProperty() {
		return userName;
	}

	public StringProperty userMajorProperty() {
		return userMajor;
	}

	public ReadOnlyBooleanProperty isWritePossibleProperty() {
		return writePossible.getReadOnlyProperty();
	}
}
