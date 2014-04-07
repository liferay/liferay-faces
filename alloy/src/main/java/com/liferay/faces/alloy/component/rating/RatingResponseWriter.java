package com.liferay.faces.alloy.component.rating;

import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

public class RatingResponseWriter extends ResponseWriterWrapper {

	private ResponseWriter wrappedResponseWriter;
	private boolean implWritingInput;
	private boolean implWritingLabel;
	private Object title;
	private String onClick;
	private int index;
	private int selectedIndex;
	private Integer defaultSelected;
	private Object defaultSelectedValue;

	public RatingResponseWriter(ResponseWriter responseWriter, Object defaultSelected) {
		this.wrappedResponseWriter = responseWriter;
		this.index = -1;
		this.selectedIndex = -1;
		this.defaultSelected = (defaultSelected == null) ? null : new Integer((String) defaultSelected);
	}

	@Override
	public ResponseWriter getWrapped() {
		return wrappedResponseWriter;
	}
	
	public String getOnClick() {
		return onClick;
	}
	
	public int getSelectedIndex() {
		return selectedIndex;
	}

	public void setSelectedIndex(int selectedIndex) {
		this.selectedIndex = selectedIndex;
	}
	
	public Object getDefaultSelectedValue() {
		return defaultSelectedValue;
	}

	public void setDefaultSelectedValue(Object defaultSelectedValue) {
		this.defaultSelectedValue = defaultSelectedValue;
	}
	
	@Override
    public void startElement(String name, UIComponent component)
            throws IOException {
//		System.err.println("startElement: " + name);
		if ("input".equalsIgnoreCase(name)) {
			implWritingInput = true;
			this.title = "";
			this.index += 1;
			super.startElement(name, component);
		}
		if ("label".equalsIgnoreCase(name)) {
			implWritingLabel = true;
		}
    }
	
	@Override
	public void writeAttribute(String name, Object value, String property)
			throws IOException {
//		System.err.println("writeAttribute: input: " + name + " = " + ((value == null) ? "" : value.toString()));
		if (implWritingInput) {
			if ("checked".equals(name)) {
				// We have found the input that is "checked"
				setSelectedIndex(this.index);
			}
			if ("value".equals(name)) {
				if (defaultSelected != null) {
					if (index == (defaultSelected-1)) {
						// We have found the input containing the value of the defaultSelected rating
						setDefaultSelectedValue(value);
					}
				}
			}
			super.writeAttribute(name, value, property);
		}
		if ("onclick".equalsIgnoreCase(name)) {
			// we have found JSF's onclick
			onClick = (String) value;
		}
	}
	
	// f:selectItem uses this for writing text into a label
	@Override
	public void writeText(Object text, UIComponent component, String property)
			throws IOException {
		if (implWritingLabel) {
//			System.err.println("writeText: text = >" + text + "< component.getClientId() = " + component.getClientId());
			this.title = text;
			// avoid writing anything from the label
			// super.writeText(text, component, property);
		}
	}
	
	// f:selectItems uses this for writing chars into a label
	@Override
	public void write(char[] chars, int off, int len) throws IOException {
		if (implWritingLabel) {
			String newString = new String(chars, off, len);
//			System.err.println("write: newString = >" + newString + "<");
			this.title = newString;
			// avoid writing anything from the label
			// super.write(chars, off, len);
		}
	}
	
	@Override
    public void endElement(String name)
            throws IOException {
//		System.err.println("endElement: " + name);
		if ("input".equalsIgnoreCase(name)) {
			implWritingInput = false;
		}
		if ("label".equalsIgnoreCase(name)) {
			implWritingLabel = false;
			
			super.writeAttribute("title", this.title, "title");
			super.endElement("input");
		}
    }
}
