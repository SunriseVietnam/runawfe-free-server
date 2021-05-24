/*
 * This file is part of the RUNA WFE project.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public License
 * as published by the Free Software Foundation; version 2.1
 * of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package ru.runa.wfe.var;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import lombok.Getter;
import lombok.Setter;
import ru.runa.wfe.commons.Utils;
import ru.runa.wfe.lang.ProcessDefinition;
import ru.runa.wfe.var.format.FormatCommons;
import ru.runa.wfe.var.format.UserTypeFormat;
import ru.runa.wfe.var.format.VariableFormat;
import ru.runa.wfe.var.format.VariableFormatContainer;
import ru.runa.wfe.var.format.VariableFormatVisitor;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class VariableDefinition implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean synthetic;
    private String name;
    private String scriptingName;
    private String description;
    private String format;
    private String formatLabel;
    private UserType userType;
    // web-service serialization limitation
    private UserType[] formatComponentUserTypes;
    private boolean publicAccess;
    private boolean editableInChat;
    private Object defaultValue;
    private VariableStoreType storeType = VariableStoreType.DEFAULT;
    private transient VariableFormat variableFormat;

    public VariableDefinition() {
    }

    public VariableDefinition(String name, String scriptingName) {
        this.name = name.intern();
        if (scriptingName == null) {
            this.scriptingName = toScriptingName(name).intern();
            this.synthetic = true;
        } else {
            this.scriptingName = scriptingName.intern();
        }
    }

    public VariableDefinition(String name, String scriptingName, VariableFormat variableFormat) {
        this(name, scriptingName);
        setFormat(variableFormat.toString().intern());
        this.variableFormat = variableFormat;
        if (variableFormat instanceof UserTypeFormat) {
            this.userType = ((UserTypeFormat) variableFormat).getUserType();
        }
        if (variableFormat instanceof VariableFormatContainer) {
            String[] componentFormats = getFormatComponentClassNames();
            this.formatComponentUserTypes = new UserType[componentFormats.length];
            for (int i = 0; i < componentFormats.length; i++) {
                this.formatComponentUserTypes[i] = ((VariableFormatContainer) variableFormat).getComponentUserType(i);
            }
        }
    }

    public VariableDefinition(String name, String scriptingName, String format, UserType userType) {
        this(name, scriptingName);
        setFormat(format);
        this.userType = userType;
    }

    public VariableDefinition(String name, String scriptingName, VariableDefinition attributeDefinition) {
        this(name, scriptingName, attributeDefinition.getFormat(), attributeDefinition.getUserType());
        this.formatComponentUserTypes = attributeDefinition.getFormatComponentUserTypes();
        setDefaultValue(attributeDefinition.getDefaultValue());
    }

    public void initComponentUserTypes(ProcessDefinition processDefinition) {
        String[] componentFormats = getFormatComponentClassNames();
        this.formatComponentUserTypes = new UserType[componentFormats.length];
        for (int i = 0; i < componentFormats.length; i++) {
            this.formatComponentUserTypes[i] = processDefinition.getUserType(componentFormats[i]);
        }
    }

    public void setDescription(String description) {
        this.description = null == description ? null : description.intern();
    }

    public String getScriptingNameWithoutDots() {
        return scriptingName.replaceAll("(\\.|\\[|\\])", "_").intern();
    }

    public VariableFormat getFormatNotNull() {
        if (variableFormat == null) {
            variableFormat = FormatCommons.create(this);
        }
        return variableFormat;
    }

    public String getFormatClassName() {
        if (format != null && format.contains(VariableFormatContainer.COMPONENT_PARAMETERS_START)) {
            int index = format.indexOf(VariableFormatContainer.COMPONENT_PARAMETERS_START);
            return format.substring(0, index);
        }
        return format;
    }

    public void setFormat(String format) {
        this.format = null == format ? null : format.intern();
    }

    public String[] getFormatComponentClassNames() {
        if (format != null && format.contains(VariableFormatContainer.COMPONENT_PARAMETERS_START)) {
            int index = format.indexOf(VariableFormatContainer.COMPONENT_PARAMETERS_START);
            String raw = format.substring(index + 1, format.length() - 1);
            return raw.split(VariableFormatContainer.COMPONENT_PARAMETERS_DELIM, -1);
        }
        return new String[0];
    }

    public Object getDefaultValue() {
        return Utils.getContainerCopy(defaultValue);
    }

    public String getFormatLabel() {
        if (formatLabel != null) {
            return formatLabel;
        }
        if (getUserType() != null) {
            return getUserType().getName();
        }
        return format;
    }

    public void setFormatLabel(String formatLabel) {
        this.formatLabel = null == formatLabel ? null : formatLabel.intern();
    }

    public boolean isUserType() {
        return getUserType() != null;
    }

    public List<VariableDefinition> expandUserType(boolean preserveComplex) {
        return expandUserType(this, this, preserveComplex);
    }

    private List<VariableDefinition> expandUserType(VariableDefinition superVariableDefinition, VariableDefinition variableDefinition,
            boolean preserveUserTypeVariables) {
        List<VariableDefinition> result = Lists.newArrayList();
        for (VariableDefinition attributeDefinition : variableDefinition.getUserType().getAttributes()) {
            String name = superVariableDefinition.getName() + UserType.DELIM + attributeDefinition.getName();
            String scriptingName = superVariableDefinition.getScriptingName() + UserType.DELIM + attributeDefinition.getScriptingName();
            VariableDefinition variable = new VariableDefinition(name, scriptingName, attributeDefinition);
            if (variable.isUserType()) {
                if (preserveUserTypeVariables) {
                    result.add(variable);
                }
                result.addAll(expandUserType(variable, attributeDefinition, preserveUserTypeVariables));
            } else {
                result.add(variable);
            }
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof VariableDefinition) {
            VariableDefinition d = (VariableDefinition) obj;
            return Objects.equal(name, d.name);
        }
        return super.equals(obj);
    }

    /**
     * Applies operation depends on variable format type.
     * 
     * @param operation
     *            Operation, applied to format.
     * @param context
     *            Operation call context. Contains additional data for operation.
     * @return Returns operation result.
     */
    public <TResult, TContext> TResult processBy(VariableFormatVisitor<TResult, TContext> operation, TContext context) {
        VariableFormat format = FormatCommons.create(this);
        return format.processBy(operation, context);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", getName()).add("format", format).toString();
    }

    public static String toScriptingName(String variableName) {
        char[] chars = variableName.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i == 0) {
                if (!Character.isJavaIdentifierStart(chars[i])) {
                    chars[i] = '_';
                }
            } else {
                if (!Character.isJavaIdentifierPart(chars[i])) {
                    chars[i] = '_';
                }
            }
            if ('$' == chars[i]) {
                chars[i] = '_';
            }
        }
        String scriptingName = String.valueOf(chars).intern();
        return scriptingName;
    }
}
