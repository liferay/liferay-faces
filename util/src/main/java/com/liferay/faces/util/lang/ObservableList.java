/**
 * Copyright (c) 2000-2015 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */
package com.liferay.faces.util.lang;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;


/**
 * @author  Neil Griffin
 */
public class ObservableList<E> implements List<E>, Observable {

	/**
	 * @author  Neil Griffin
	 */
	public static enum Action {
		ADD, ADD_ALL, CLEAR, REMOVE, REMOVE_ALL, RETAIN_ALL, SET
	}

	// Private Data Members
	private List<Observer> observerList;
	private List<E> wrappedList;

	public ObservableList(List<E> list, List<Observer> observerList) {
		this.wrappedList = list;
		this.observerList = observerList;
	}

	public boolean add(E e) {
		boolean result = wrappedList.add(e);
		notifyObservers(Action.ADD, e);

		return result;
	}

	public void add(int index, E e) {
		wrappedList.add(index, e);
		notifyObservers(Action.ADD, e);
	}

	public boolean addAll(Collection<? extends E> c) {
		boolean all = wrappedList.addAll(c);
		notifyObservers(Action.ADD_ALL, c);

		return all;
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		boolean all = wrappedList.addAll(c);
		notifyObservers(Action.ADD_ALL, c);

		return all;
	}

	public void addObserver(Observer observer) {
		observerList.add(observer);
	}

	public void clear() {
		wrappedList.clear();
		notifyObservers(Action.CLEAR);
	}

	public boolean contains(Object o) {
		return wrappedList.contains(o);
	}

	public boolean containsAll(Collection<?> c) {
		return wrappedList.containsAll(c);
	}

	public E get(int index) {
		return wrappedList.get(index);
	}

	public int indexOf(Object o) {
		return wrappedList.indexOf(o);
	}

	public Iterator<E> iterator() {
		return wrappedList.iterator();
	}

	public int lastIndexOf(Object o) {
		return wrappedList.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return wrappedList.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return wrappedList.listIterator(index);
	}

	public void notifyObservers(Object... args) {

		for (Observer observer : observerList) {
			observer.receiveNotification(this, args);
		}
	}

	public boolean remove(Object o) {
		boolean result = wrappedList.remove(o);
		notifyObservers(Action.REMOVE, o);

		return result;
	}

	public E remove(int index) {
		E e = wrappedList.remove(index);
		notifyObservers(Action.REMOVE, e);

		return e;
	}

	public boolean removeAll(Collection<?> c) {
		boolean result = wrappedList.removeAll(c);
		notifyObservers(Action.REMOVE_ALL, c);

		return result;
	}

	public void removeObserver(Observer observer) {
		observerList.remove(observer);
		notifyObservers(Action.REMOVE, observer);
	}

	public boolean retainAll(Collection<?> c) {
		boolean result = wrappedList.retainAll(c);
		notifyObservers(Action.RETAIN_ALL, c);

		return result;
	}

	public E set(int index, E e) {
		E result = wrappedList.set(index, e);
		notifyObservers(Action.SET, index, e);

		return result;
	}

	public int size() {
		return wrappedList.size();
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return wrappedList.subList(fromIndex, toIndex);
	}

	public Object[] toArray() {
		return wrappedList.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return wrappedList.toArray(a);
	}

	public boolean isEmpty() {
		return wrappedList.isEmpty();
	}

}
