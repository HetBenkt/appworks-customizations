package nl.bos.item;

public class ItemFactory {
    public IItem getItem(EItemTypes itemType) {
        if (itemType == null) {
            return null;
        }

        if (itemType == EItemTypes.ENTITY) {
            return new Entity();
        } else if (itemType == EItemTypes.DOCUMENT) {
            return new Document();
        } else if (itemType == EItemTypes.CONTENT) {
            return new Content();
        }

        return null;
    }
}
