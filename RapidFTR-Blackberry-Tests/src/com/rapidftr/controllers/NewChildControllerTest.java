package com.rapidftr.controllers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import com.rapidftr.datastore.ChildrenRecordStore;
import com.rapidftr.datastore.FormStore;
import com.rapidftr.model.Child;
import com.rapidftr.screens.NewChildScreen;
import com.rapidftr.screens.UiStack;

public class NewChildControllerTest {

	private FormStore formStore;
	private UiStack uiStack;
	private NewChildScreen newChildScreen;
	private NewChildController newChildController;
	private Vector forms;
	private Dispatcher dispatcher;
	private ChildrenRecordStore ChildRecordStore;

	@Before
	public void setUp() {
		formStore = mock(FormStore.class);
		uiStack = mock(UiStack.class);
		forms = mock(Vector.class);
		ChildRecordStore = mock(ChildrenRecordStore.class);
		when(formStore.getForms()).thenReturn(forms);
		newChildScreen = mock(NewChildScreen.class);
		newChildController = new NewChildController(newChildScreen, uiStack,
				formStore,ChildRecordStore);
		dispatcher = mock(Dispatcher.class);
		newChildController.setDispatcher(dispatcher);
	}
	
	@Test
	public void shouldDelegateSynchronizeFormActionToDispatcher()
	{
		newChildController.synchronizeForms();
		verify(dispatcher).synchronizeForms();
	}
	
	@Test
	public void shouldSaveTheChildToTheRecordStore()
	{
		
	Child child = mock(Child.class);
		newChildController.saveChild(child);
	}


}
