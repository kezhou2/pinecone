package com.tenline.pinecone.platform.web.store.client.widgets;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionEvent;
import com.extjs.gxt.ui.client.mvc.AppEvent;
import com.extjs.gxt.ui.client.mvc.Dispatcher;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.util.Format;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.GridViewConfig;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.BoxLayout.BoxLayoutPack;
import com.extjs.gxt.ui.client.widget.layout.CardLayout;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.HBoxLayoutData;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout;
import com.extjs.gxt.ui.client.widget.layout.VBoxLayout.VBoxLayoutAlign;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.tenline.pinecone.platform.model.Mail;
import com.tenline.pinecone.platform.model.User;
import com.tenline.pinecone.platform.web.store.client.Messages;
import com.tenline.pinecone.platform.web.store.client.controllers.MailController;
import com.tenline.pinecone.platform.web.store.client.events.MailEvents;
import com.tenline.pinecone.platform.web.store.client.events.WidgetEvents;

public class MailListViewport extends AbstractViewport {

	private MainPanel mainPanel;

	public MailListViewport() {
		mainPanel = new MainPanel();
		BorderLayoutData bld = new BorderLayoutData(LayoutRegion.CENTER);
		bld.setMargins(new Margins(10, 10, 10, 10));
		body.add(mainPanel, bld);
	}

	public void loadInfo() {
		AppEvent event = new AppEvent(MailEvents.GET_UNREAD);
		Dispatcher.get().dispatch(event);
	}

	public void loadMails(Collection<BeanModel> inMails) {
		mainPanel.loadMails(inMails);
	}

	private class MainPanel extends ContentPanel {

		private Grid<BeanModel> inboxGrid;
		private Grid<BeanModel> outboxGrid;
		private ListStore<BeanModel> inListStore = new ListStore<BeanModel>();
		private ListStore<BeanModel> outListStore = new ListStore<BeanModel>();

		private LayoutContainer container;
		private CardLayout layout;
		private ContentPanel inboxPanel = null;

		public MainPanel() {
			setHeaderVisible(false);
			setLayout(new BorderLayout());
			this.setBodyStyleName("abstractviewport-background");
			this.setBodyBorder(false);
			this.setBorders(false);

			BorderLayoutData toolbarbld = new BorderLayoutData(
					LayoutRegion.WEST, 100);
			toolbarbld.setMargins(new Margins(10, 0, 10, 10));
			LayoutContainer toolbarlc = new LayoutContainer();
			add(toolbarlc, toolbarbld);
			toolbarlc.addStyleName("abstractviewport-background");

			VBoxLayout vbl_toolbarLayoutContainer = new VBoxLayout();
			vbl_toolbarLayoutContainer.setPack(BoxLayoutPack.START);
			vbl_toolbarLayoutContainer
					.setVBoxLayoutAlign(VBoxLayoutAlign.STRETCHMAX);
			toolbarlc.setLayout(vbl_toolbarLayoutContainer);

			com.google.gwt.user.client.ui.Button btnInbox = new com.google.gwt.user.client.ui.Button(
					((Messages) Registry.get(Messages.class.getName()))
							.HomeViewport_title());
			btnInbox.setHTML("<img class='btn-img-left' src='../images/icons/inbox.png'>"
					+ ((Messages) Registry.get(Messages.class.getName()))
							.MailListViewport_tabitem_inbox() + "</img>");
			btnInbox.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent event1 = new AppEvent(
							WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					event1.setHistoryEvent(true);
					Dispatcher.get().dispatch(event1);
				}
			});
			btnInbox.setHeight("32px");
			btnInbox.setWidth("80px");
			btnInbox.setStyleName("abstractviewport-btn");
			toolbarlc.add(btnInbox);

			container = new LayoutContainer();
			container.setBorders(false);
			BorderLayoutData centerlc = new BorderLayoutData(
					LayoutRegion.CENTER);
			centerlc.setMargins(new Margins(10, 10, 10, 0));
			add(container, centerlc);

			layout = new CardLayout();
			container.setLayout(layout);

			container.add(createInboxPanel(), new FitData(0, 0, 10, 0));
			layout.setActiveItem(createInboxPanel());
			// send box
			// TabItem tbtmSendbox = new TabItem("Outbox");
			// tbtmSendbox.setLayout(new FitLayout());
			// tabPanel.add(tbtmSendbox);
			//
			// {
			// List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
			//
			// GridCellRenderer<BeanModel> senderRender = new
			// GridCellRenderer<BeanModel>() {
			// @Override
			// public Object render(BeanModel model, String property,
			// ColumnData config, int rowIndex, int colIndex,
			// ListStore<BeanModel> store, Grid<BeanModel> grid) {
			// User receiver = (User)model.get("receiver");
			// return receiver.getName();
			// }
			//
			// };
			// ColumnConfig clmncnfgNewColumn = new ColumnConfig("receiver",
			// "收件人", 80);
			// configs.add(clmncnfgNewColumn);
			// clmncnfgNewColumn.setRenderer(senderRender);
			//
			// GridCellRenderer<BeanModel> titleRender = new
			// GridCellRenderer<BeanModel>() {
			// @Override
			// public Object render(BeanModel model, String property,
			// ColumnData config, int rowIndex, int colIndex,
			// ListStore<BeanModel> store, Grid<BeanModel> grid) {
			// return model.get("title");
			// }
			//
			// };
			// ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig("title",
			// "邮件标题", 100);
			// configs.add(clmncnfgNewColumn_1);
			// clmncnfgNewColumn_1.setRenderer(titleRender);
			//
			// GridCellRenderer<BeanModel> contentRender = new
			// GridCellRenderer<BeanModel>() {
			// @Override
			// public Object render(BeanModel model, String property,
			// ColumnData config, int rowIndex, int colIndex,
			// ListStore<BeanModel> store, Grid<BeanModel> grid) {
			// return Format.ellipse(model.get("content").toString(),30);
			// }
			// };
			// ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig("content",
			// "内容", 200);
			// configs.add(clmncnfgNewColumn_2);
			// clmncnfgNewColumn_2.setRenderer(contentRender);
			//
			// ColumnModel columnModel = new ColumnModel(configs);
			// outboxGrid = new Grid<BeanModel>(outListStore, columnModel);
			// outboxGrid.addListener(Events.RowDoubleClick, new
			// Listener<GridEvent>() {
			// public void handleEvent(GridEvent e) {
			//
			// }
			// });
			// tbtmSendbox.add(outboxGrid);
			// outboxGrid.setBorders(true);
			// }

			// tool bar
			ToolBar toolBar = new ToolBar();
			toolBar.setBorders(false);
			toolBar.addStyleName("abstractviewport-background");
			toolBar.setHeight("40px");

			LayoutContainer lc = new LayoutContainer();
			lc.setLayout(new FitLayout());
			Button btnBack = new Button(((Messages) Registry.get(Messages.class
					.getName())).MailListViewport_button_back());
			lc.add(btnBack);
			toolBar.add(lc);
			btnBack.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(
							WidgetEvents.UPDATE_USERHOME_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			btnBack.setHTML("<img class='btn-img-left' src='../images/icons/back.png'>"
					+ ((Messages) Registry.get(Messages.class.getName()))
							.HomeViewport_title() + "</img>");
			btnBack.setHeight("32px");
			btnBack.setStyleName("abstractviewport-btn");

			FillToolItem fillToolItem = new FillToolItem();
			toolBar.add(fillToolItem);

			LayoutContainer lc1 = new LayoutContainer();
			HBoxLayout hbl_lc1 = new HBoxLayout();
			hbl_lc1.setPack(BoxLayoutPack.END);
			lc1.setLayout(hbl_lc1);
			lc1.setWidth("140");
			Button btnNewbox = new Button();
			btnNewbox
					.setHTML("<img class='btn-img-center' src='../images/icons/current-work.png' />");
			btnNewbox.setTitle(((Messages) Registry.get(Messages.class
					.getName())).MailListViewport_button_new());
			btnNewbox.setSize("60px", "32px");
			btnNewbox.addStyleName("abstractviewport-btn");
			btnNewbox.addMouseUpHandler(new MouseUpHandler() {
				public void onMouseUp(MouseUpEvent event) {
					AppEvent appEvent = new AppEvent(
							WidgetEvents.UPDATE_CREATE_MAIL_TO_PANEL);
					appEvent.setHistoryEvent(true);
					Dispatcher.get().dispatch(appEvent);
				}

			});
			lc1.add(btnNewbox, new HBoxLayoutData(0, 2, 0, 2));
			toolBar.add(lc1);

			Button btnDelete = new Button();
			btnDelete
					.setHTML("<img class='btn-img-center' src='../images/icons/delete.png' />");
			btnDelete.setTitle(((Messages) Registry.get(Messages.class
					.getName())).MailListViewport_button_delete());
			btnDelete.setSize("60px", "32px");
			btnDelete.addStyleName("abstractviewport-btn");
			lc1.add(btnDelete, new HBoxLayoutData(0, 2, 0, 2));
			setTopComponent(toolBar);
		}

		public void loadMails(Collection<BeanModel> inMails) {
			inListStore.removeAll();
			inListStore.add(new ArrayList<BeanModel>(inMails));
			inListStore.commitChanges();
		}

		public ContentPanel createInboxPanel() {

			if (inboxPanel == null) {
				inboxPanel = new ContentPanel();
				inboxPanel.setHeading(((Messages) Registry.get(Messages.class
						.getName())).MailListViewport_tabitem_inbox());
				inboxPanel.getHeader().addStyleName("header-title");
				inboxPanel.getHeader().addStyleName(
						"appstoreviewport-panel-header");
				inboxPanel.setLayout(new FitLayout());
				inboxPanel.setBodyBorder(false);
				inboxPanel.setBorders(false);
				inboxPanel.addStyleName("appstoreviewport-panel");

				{
					List<ColumnConfig> configs = new ArrayList<ColumnConfig>();

					GridCellRenderer<BeanModel> statusRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							return model.get("isRead");
						}

					};
					ColumnConfig columnConfig = new ColumnConfig("isRead",
							((Messages) Registry.get(Messages.class.getName()))
									.MailListViewport_column_status(), 60);
					configs.add(columnConfig);
					columnConfig.setRenderer(statusRender);

					GridCellRenderer<BeanModel> senderRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							Mail mail = (Mail) model
									.get(MailController.MAIL_INSTANCE);
							return mail.getSender().getName();
						}

					};
					ColumnConfig clmncnfgNewColumn = new ColumnConfig("sender",
							((Messages) Registry.get(Messages.class.getName()))
									.MailListViewport_column_sender(), 80);
					configs.add(clmncnfgNewColumn);
					clmncnfgNewColumn.setRenderer(senderRender);

					GridCellRenderer<BeanModel> titleRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							return model.get("title");
						}

					};
					ColumnConfig clmncnfgNewColumn_1 = new ColumnConfig(
							"title",
							((Messages) Registry.get(Messages.class.getName()))
									.MailListViewport_column_title(), 100);
					configs.add(clmncnfgNewColumn_1);
					clmncnfgNewColumn_1.setRenderer(titleRender);

					GridCellRenderer<BeanModel> contentRender = new GridCellRenderer<BeanModel>() {
						@Override
						public Object render(BeanModel model, String property,
								ColumnData config, int rowIndex, int colIndex,
								ListStore<BeanModel> store, Grid<BeanModel> grid) {
							return Format.ellipse(model.get("content")
									.toString(), 30);
						}
					};
					ColumnConfig clmncnfgNewColumn_2 = new ColumnConfig(
							"content",
							((Messages) Registry.get(Messages.class.getName()))
									.MailListViewport_column_content(), 200);
					configs.add(clmncnfgNewColumn_2);
					clmncnfgNewColumn_2.setRenderer(contentRender);

					ColumnModel columnModel = new ColumnModel(configs);
					inboxGrid = new Grid<BeanModel>(inListStore, columnModel);
					inboxPanel.add(inboxGrid);
					inboxGrid.setBorders(false);
					inboxGrid.setHideHeaders(true);

					inboxGrid.getView().setViewConfig(new GridViewConfig() {
						@Override
						public String getRowStyle(ModelData model,
								int rowIndex, ListStore<ModelData> ds) {
							Mail mail = (Mail)model.get(MailController.MAIL_INSTANCE);
							if (mail.isRead()) {
								return "mail-grid-row-read";
							} else {
								return "mail-grid-row-unread";
							}
						}
					});

					inboxGrid.addListener(Events.RowClick, new Listener<GridEvent<BeanModel>>() {
						public void handleEvent(GridEvent<BeanModel> e) {
							System.out.println("MailListViewport DoubleClick");
							BeanModel bm = e.getGrid().getSelectionModel().getSelectedItem();
							AppEvent e1 = new AppEvent(WidgetEvents.UPDATE_READ_MAIL_TO_PANEL);
							e1.setData(bm);
							Dispatcher.get().dispatch(e1);
						}
					});
				}
			}

			return inboxPanel;
		}

	}

}
