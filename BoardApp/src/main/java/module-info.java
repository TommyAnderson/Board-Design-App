module edu.augustana.egret.BoardEditor {
    requires javafx.controls;
    requires javafx.fxml;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;
	requires java.xml;
	requires com.google.gson;

    opens edu.augustana.egret.BoardEditor to javafx.fxml, com.google.gson;
    exports edu.augustana.egret.BoardEditor;
    
    opens edu.augustana.egret.BoardEditorData to javafx.fxml, com.google.gson;
    exports edu.augustana.egret.BoardEditorData;

    
    
    
}
