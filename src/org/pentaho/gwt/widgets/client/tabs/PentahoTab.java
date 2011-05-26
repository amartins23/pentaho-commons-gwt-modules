package org.pentaho.gwt.widgets.client.tabs;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class PentahoTab extends SimplePanel {

  private PentahoTabPanel tabPanel;
  private Widget content;
  private Label label = new Label();

  public PentahoTab(String text, String tooltip, PentahoTabPanel tabPanel, Widget content, boolean closeable) {
    this.content = content;
    this.tabPanel = tabPanel;
    setStylePrimaryName("pentaho-tabWidget");
    sinkEvents(Event.ONDBLCLICK | Event.ONMOUSEUP);

    if (closeable) {
      final Image closeTabImage = PentahoTabImages.images.tab_close_off().createImage();
      closeTabImage.setStyleName("pentaho-tabWidget-close");
      closeTabImage.addClickHandler(new ClickHandler() {
        public void onClick(ClickEvent event) {
          event.getNativeEvent().stopPropagation();
          closeTab();
        }
      });
      closeTabImage.addMouseOverHandler(new MouseOverHandler() {
        public void onMouseOver(MouseOverEvent event) {
          PentahoTabImages.images.tab_close_on().applyTo(closeTabImage);
        }
      });
      closeTabImage.addMouseOutHandler(new MouseOutHandler() {

        public void onMouseOut(MouseOutEvent event) {
          PentahoTabImages.images.tab_close_off().applyTo(closeTabImage);
        }
      });

      HorizontalPanel p = new HorizontalPanel();
      setupLabel(text, tooltip);
      p.add(label);
      p.add(closeTabImage);
      setWidget(p);
    } else {
      setupLabel(text, tooltip);
      setWidget(label);
    }
  }

  public void setupLabel(String text, String tooltip) {
    label.setText(text);
    label.setTitle(tooltip);
    label.setStylePrimaryName("pentaho-tabWidgetLabel");
  }

  public Widget getContent() {
    return content;
  }

  public void setContent(Widget content) {
    this.content = content;
  }

  protected PentahoTabPanel getTabPanel() {
    return tabPanel;
  }

  protected void setTabPanel(PentahoTabPanel tabPanel) {
    this.tabPanel = tabPanel;
  }

  public void onBrowserEvent(Event event) {
    if ((DOM.eventGetType(event) & Event.ONDBLCLICK) == Event.ONDBLCLICK) {
      onDoubleClick(event);
    } else if (DOM.eventGetButton(event) == Event.BUTTON_RIGHT) {
      onRightClick(event);
    } else if (DOM.eventGetButton(event) == Event.BUTTON_LEFT) {
      if (event.getEventTarget().toString().toLowerCase().indexOf("image") == -1) {
        fireTabSelected();
      }
    }
    super.onBrowserEvent(event);
  }

  public void onDoubleClick(Event event) {
  }

  public void onRightClick(Event event) {
  }

  public void setSelected(boolean selected) {
    if (selected) {
      addStyleDependentName("selected");
    } else {
      removeStyleDependentName("selected");
    }
  }

  public String getLabelText() {
    return label.getText();
  }

  public void setLabelText(String text) {
    label.setText(text);
  }

  public void setLabelTooltip(String tooltip) {
    label.setTitle(tooltip);
  }

  public String getLabelTooltip() {
    return label.getTitle();
  }

  protected void closeTab() {
    tabPanel.closeTab(this, true);
  }

  protected void fireTabSelected() {
    tabPanel.selectTab(this);
  }

}
