package liquibase.statement.core;

import liquibase.statement.*;
import liquibase.structure.DatabaseObject;
import liquibase.structure.core.Column;
import liquibase.structure.core.Schema;
import liquibase.structure.core.Table;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Adds a column to an existing table.
 */
public class AddColumnStatement extends AbstractColumnStatement {

    private final String COLUMN_TYPE = "columnType";
    private final String DEFAULT_VALUE = "defaultValue";
    private final String REMARKS = "remarks";
    private final String ADD_AFTER_COLUMN = "addAfterColumn";
    private final String ADD_BEFORE_COLUMN = "addBeforeColumn";
    private final String ADD_AT_POSITION = "addAtPosition";
    private final String CONSTRAINTS = "constraints";

    public AddColumnStatement() {

    }

    public AddColumnStatement(String catalogName, String schemaName, String tableName, String columnName, String columnType, Object defaultValue, ColumnConstraint... constraints) {
        super(catalogName, schemaName, tableName, columnName);
        setAttribute(CONSTRAINTS, new HashSet<ColumnConstraint>());
        setColumnType(columnType);
        setDefaultValue(defaultValue);
        if (constraints != null) {
            getConstraints().addAll(Arrays.asList(constraints));
        }
    }

    public String getColumnType() {
        return getAttribute(COLUMN_TYPE, String.class);
    }

    public AddColumnStatement setColumnType(String columnType) {
        return (AddColumnStatement) setAttribute(COLUMN_TYPE, columnType);
    }


    public String getRemarks() {
        return getAttribute(REMARKS, String.class);
    }

    public AddColumnStatement setRemarks(String remarks) {
        return (AddColumnStatement) setAttribute(REMARKS, remarks);
    }


    public Set<ColumnConstraint> getConstraints() {
        return (Set<ColumnConstraint>) getAttribute(CONSTRAINTS, Set.class);
    }

    public AddColumnStatement addConstraint(ColumnConstraint constraint) {
        getConstraints().add(constraint);
        return this;
    }


    @Override
    protected DatabaseObject[] getBaseAffectedDatabaseObjects() {
        return new DatabaseObject[] {
                new Column()
                        .setRelation(new Table().setName(getTableName()).setSchema(new Schema(getCatalogName(), getSchemaName())))
                        .setName(getColumnName())
        };
    }


    /**
     * Convenience method to search the defined constraints for an {@link liquibase.statement.AutoIncrementConstraint}
     */
    public boolean isAutoIncrement() {
        for (ColumnConstraint constraint : getConstraints()) {
            if (constraint instanceof AutoIncrementConstraint) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convenience method to search the defined constraints for an {@link liquibase.statement.AutoIncrementConstraint}
     */
    public AutoIncrementConstraint getAutoIncrementConstraint() {
        AutoIncrementConstraint autoIncrementConstraint = null;
        
        for (ColumnConstraint constraint : getConstraints()) {
            if (constraint instanceof AutoIncrementConstraint) {
                autoIncrementConstraint = (AutoIncrementConstraint) constraint;
                break;
            }
        }
        
        return autoIncrementConstraint;
    }

    /**
     * Convenience method to search the defined constraints for a {@link liquibase.statement.PrimaryKeyConstraint}
     */
    public boolean isPrimaryKey() {
        for (ColumnConstraint constraint : getConstraints()) {
            if (constraint instanceof PrimaryKeyConstraint) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convenience method to search the defined constraints for a {@link liquibase.statement.NotNullConstraint}
     */
    public boolean isNullable() {
        if (isPrimaryKey()) {
            return false;
        }
        for (ColumnConstraint constraint : getConstraints()) {
            if (constraint instanceof NotNullConstraint) {
                return false;
            }
        }
        return true;
    }

    /**
     * Convenience method to search the defined constraints for an {@link liquibase.statement.UniqueConstraint}
     */
    public boolean isUnique() {
        for (ColumnConstraint constraint : getConstraints()) {
            if (constraint instanceof UniqueConstraint) {
                return true;
            }
        }
        return false;
    }

    /**
     * Convenience method to search the defined constraints for an {@link liquibase.statement.UniqueConstraint}
     * Returns null if no unique constraint was defined.
     */
    public String getUniqueConstraintName() {
        for (ColumnConstraint constraint : getConstraints()) {
            if (constraint instanceof UniqueConstraint) {
                return ((UniqueConstraint) constraint).getConstraintName();
            }
        }
        return null;
    }

    public Object getDefaultValue() {
        return getAttribute(DEFAULT_VALUE, Object.class);
    }
    public AddColumnStatement setDefaultValue(Object defaultValue) {
        return (AddColumnStatement) setAttribute(DEFAULT_VALUE, defaultValue);
    }

    public String getAddAfterColumn() {
        return getAttribute(ADD_AFTER_COLUMN, String.class);
    }

    public AddColumnStatement setAddAfterColumn(String addAfterColumn) {
        return (AddColumnStatement) setAttribute(ADD_AFTER_COLUMN, addAfterColumn);
    }

    public String getAddBeforeColumn() {
        return getAttribute(ADD_BEFORE_COLUMN, String.class);
    }

    public AddColumnStatement setAddBeforeColumn(String addBeforeColumn) {
        return (AddColumnStatement) setAttribute(ADD_BEFORE_COLUMN, addBeforeColumn);
	}

	public Integer getAddAtPosition() {
        return getAttribute(ADD_AT_POSITION, Integer.class);
    }

	public AddColumnStatement setAddAtPosition(Integer addAtPosition) {
        return (AddColumnStatement) setAttribute(ADD_AT_POSITION, addAtPosition);
	}
}
