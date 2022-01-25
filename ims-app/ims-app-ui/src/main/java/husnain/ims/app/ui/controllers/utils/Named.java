package husnain.ims.app.ui.controllers.utils;

/**
 *
 * @author Husnain Arif
 */
public interface Named {

    @Override
    public String toString();

    public enum DialogType implements Named {
        ADD("Add"), MODIFY("Modify");
        private final String typeName;

        private DialogType(String typeName) {
            this.typeName = typeName;
        }

        @Override
        public String toString() {
            return typeName;
        }

    }
}
