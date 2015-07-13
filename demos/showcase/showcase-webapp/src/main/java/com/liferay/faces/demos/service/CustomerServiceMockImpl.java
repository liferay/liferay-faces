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
package com.liferay.faces.demos.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
// JSF 2: import javax.faces.bean.ApplicationScoped;
// JSF 2: import javax.faces.bean.ManagedBean;
// JSF 2: import javax.faces.bean.ManagedProperty;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;

import com.liferay.faces.demos.dto.Country;
import com.liferay.faces.demos.dto.Customer;


/**
 * @author  Neil Griffin
 */
// JSF 2: @ApplicationScoped
// JSF 2: @ManagedBean(name = "customerService")
public class CustomerServiceMockImpl implements CustomerService, Serializable {

	// serialVersionUID
	private static final long serialVersionUID = 2241487919972957504L;

	// Private Data Members
	private List<Customer> allCustomers;

	// Injections
	// JSF 2: @ManagedProperty(value = "#{countryService}")
	private CountryService countryService;

	@PostConstruct
	public void postConstruct() {

		Country unitedStates = getCountryService().getCountryByCode("US");

		Country unitedKingdom = getCountryService().getCountryByCode("UK");

		allCustomers = new ArrayList<Customer>();

		long userId = 0;

		// US
		allCustomers.add(new Customer(userId++, unitedStates, "John", "Adams", getDate(10, 30, 1735)));
		allCustomers.add(new Customer(userId++, unitedStates, "Samuel", "Adams", getDate(9, 27, 1722)));
		allCustomers.add(new Customer(userId++, unitedStates, "Josiah", "Bartlett", getDate(11, 21, 1729)));
		allCustomers.add(new Customer(userId++, unitedStates, "Carter", "Braxton", getDate(9, 16, 1736)));
		allCustomers.add(new Customer(userId++, unitedStates, "Charles", "Carroll", getDate(9, 19, 1737)));
		allCustomers.add(new Customer(userId++, unitedStates, "Benjamin", "Franklin", getDate(1, 17, 1706)));
		allCustomers.add(new Customer(userId++, unitedStates, "Elbridge", "Gerry", getDate(2, 3, 1701)));
		allCustomers.add(new Customer(userId++, unitedStates, "John", "Hancock", getDate(1, 23, 1737)));
		allCustomers.add(new Customer(userId++, unitedStates, "Thomas", "Jefferson", getDate(4, 13, 1743)));
		allCustomers.add(new Customer(userId++, unitedStates, "Francis", "Lewis", getDate(3, 21, 1713)));
		allCustomers.add(new Customer(userId++, unitedStates, "Philip", "Livingston", getDate(1, 15, 1716)));
		allCustomers.add(new Customer(userId++, unitedStates, "Thomas", "Stone", getDate(1, 1, 1743)));
		allCustomers.add(new Customer(userId++, unitedStates, "George", "Taylor", getDate(2, 2, 1716)));
		allCustomers.add(new Customer(userId++, unitedStates, "George", "Washington", getDate(2, 22, 1732)));
		allCustomers.add(new Customer(userId++, unitedStates, "John", "Witherspoon", getDate(2, 5, 1723)));
		allCustomers.add(new Customer(userId++, unitedStates, "Oliver", "Wolcott", getDate(11, 20, 1726)));
		allCustomers.add(new Customer(userId++, unitedStates, "George", "Wythe", getDate(3, 3, 1726)));
		allCustomers.add(new Customer(userId++, unitedStates, "Thomas", "McKean", getDate(3, 19, 1734)));
		allCustomers.add(new Customer(userId++, unitedStates, "Arthur", "Middleton", getDate(6, 26, 1742)));
		allCustomers.add(new Customer(userId++, unitedStates, "John", "Penn", getDate(5, 17, 1741)));
		allCustomers.add(new Customer(userId++, unitedStates, "George", "Read", getDate(9, 18, 1733)));
		allCustomers.add(new Customer(userId++, unitedStates, "John", "Rutledge", getDate(9, 17, 1739)));
		allCustomers.add(new Customer(userId++, unitedStates, "Roger", "Sherman", getDate(4, 19, 1721)));

		// UK
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Hardyman", getDate(3, 24, 1575)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Pullain", getDate(4, 27, 1566)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Miles", "Hawkins", getDate(12, 12, 1608)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Robert", "Coverdale", getDate(2, 3, 1603)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Andrew", "Colman", getDate(3, 4, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Christopher", "Kingsmill", getDate(3, 29, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Becon", getDate(4, 4, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Axton", getDate(5, 5, 1587)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Bonham", getDate(8, 8, 1612)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Robert", "Johnson", getDate(9, 9, 1577)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Adoniram", "Taverner", getDate(4, 5, 1665)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Bale", getDate(2, 21, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Richard", "Byfield", getDate(10, 10, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Aldrich", getDate(2, 1, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Whittingham", getDate(6, 7, 1586)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Francis", "Merbury", getDate(4, 6, 1597)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Lever", getDate(5, 7, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Robert", "Wright", getDate(6, 9, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Bernard", "Gilpin", getDate(7, 9, 1597)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Underdown", getDate(8, 10, 1608)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Hill", getDate(9, 11, 1590)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Nicholas", "Brown", getDate(10, 12, 1601)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Richard", "Crick", getDate(11, 10, 1604)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Anthony", "Gilby", getDate(11, 7, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Brayne", getDate(2, 8, 1586)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Edwin", getDate(11, 6, 1596)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Copping", getDate(7, 8, 1608)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Barnaby", "Benison", getDate(8, 9, 1585)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Cuthbery", "Brainbrigg", getDate(5, 5, 1613)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Willaim", "Bergland", getDate(9, 10, 1605)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Stroud", getDate(10, 11, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "David", "Rockrey", getDate(7, 7, 1606)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Browning", getDate(11, 12, 1598)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Ward", getDate(12, 13, 1587)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Gilbert", "Alcock", getDate(6, 6, 1582)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edmund", "Whitehead", getDate(2, 4, 1608)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Deering", getDate(5, 6, 1598)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Robert", "Moore", getDate(3, 5, 1606)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Gellibrand", getDate(11, 5, 1598)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Gardiner", getDate(5, 30, 1601)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Nicholas", "Glover", getDate(11, 11, 1592)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Field", getDate(11, 14, 1575)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Huckle", getDate(10, 13, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Standen", getDate(11, 4, 1603)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Fox", getDate(9, 12, 1641)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Wilson", getDate(8, 11, 1597)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Elliston", getDate(8, 10, 1592)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Robert", "Crowley", getDate(7, 9, 1604)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Turner", getDate(1, 2, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Nicholas", "Crane", getDate(6, 8, 1605)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Lawnrence", "Humprey", getDate(11, 1, 1595)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Sampson", getDate(8, 15, 1582)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Fulke", getDate(9, 16, 1574)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Garbrand", getDate(3, 5, 1580)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Dudley", "Fenner", getDate(4, 6, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edmund", "Littleton", getDate(8, 14, 1587)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Larding", getDate(9, 13, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Andrew", "Kingston", getDate(8, 2, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Malancthon", "Jewell", getDate(2, 4, 1597)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Snape", getDate(3, 13, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Holmes", getDate(2, 16, 1584)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Richard", "Greenham", getDate(2, 17, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Giles", "Wigginton", getDate(2, 18, 1596)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Barber", getDate(8, 3, 1616)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Robert", "Cawdrey", getDate(9, 6, 1665)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Lever", "Wood", getDate(10, 9, 1572)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Hymphrey", "Fenn", getDate(11, 12, 1643)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Daniel", "Wright", getDate(12, 15, 1573)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Proudlove", getDate(10, 12, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "More", getDate(4, 12, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Lyford", getDate(2, 22, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Obadiah", "de la Marche", getDate(11, 11, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Maynard", getDate(2, 19, 1597)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Jean", "Sedgwick", getDate(2, 20, 1585)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Stephen", "Hickes", getDate(2, 21, 1594)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Richard", "Hildersha", getDate(1, 3, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Gaspar", "Heyrick", getDate(12, 5, 1605)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Samuel", "Marshall", getDate(11, 6, 1594)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Hodges", getDate(10, 7, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Hill", getDate(9, 8, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Richard", "Hoyle", getDate(7, 9, 1590)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Joshua", "Holdsworth", getDate(6, 10, 1588)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Henry", "Chambers", getDate(5, 11, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Humphrey", "Hutton", getDate(1, 3, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Perne", getDate(7, 27, 1583)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Andrew", "Peale", getDate(8, 26, 1595)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Pickering", getDate(9, 25, 1585)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Benjamin", "de la Place", getDate(10, 24, 1620)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Samuel", "Price", getDate(11, 23, 1576)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Philip", getDate(12, 22, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Nicholas", "Delme", getDate(1, 21, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Philippe", "Prophet", getDate(12, 9, 1653)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Calybute", "Mew", getDate(11, 8, 1606)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Downing", getDate(2, 23, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Moreton", getDate(2, 24, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Macklet", getDate(2, 25, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Whiddon", getDate(6, 7, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "George", "Newcomen", getDate(2, 26, 1598)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Josias", "Walker", getDate(12, 10, 1588)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Matthew", "Morley", getDate(2, 27, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Anthony", "Langley", getDate(2, 25, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Daniel", "Ford", getDate(11, 3, 1582)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Featley", getDate(11, 2, 1598)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Nye", getDate(2, 28, 1591)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Richard", "Valentine", getDate(6, 21, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Henry", "Nicholson", getDate(3, 31, 1589)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "James", "Harris", getDate(4, 7, 1581)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Philip", "Nye", getDate(4, 30, 1595)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Henry", "Palmer", getDate(5, 29, 1583)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Rathbone", getDate(1, 15, 1588)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Launce", getDate(3, 19, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Reynolds", getDate(4, 18, 1595)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Edward", "Rayne", getDate(5, 17, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Herbert", "Painter", getDate(6, 28, 1601)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Henry", "Sallaway", getDate(6, 16, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Arthur", "Roborough", getDate(7, 15, 1606)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Francis", "Templeton", getDate(9, 1, 1589)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Taylor", getDate(10, 5, 1601)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Tisdale", getDate(11, 10, 1595)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Christopher", "Thorowgood", getDate(12, 15, 1592)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Henry", "Pyne", getDate(1, 20, 1601)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Tozer", getDate(2, 20, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Tuckney", getDate(2, 14, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Gibbon", getDate(3, 30, 1577)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Twisse", getDate(10, 17, 1587)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "George", "Goodwin", getDate(11, 18, 1590)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Samuel", "Goodard", getDate(12, 19, 1580)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Gibson", getDate(12, 20, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Hardwick", getDate(12, 21, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Humphrey", "Gibbs", getDate(5, 7, 1602)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Matthias", "Strickland", getDate(6, 4, 1591)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Stiles", getDate(7, 3, 1601)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Francis", "Wincop", getDate(1, 18, 1599)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "John", "Usher", getDate(4, 6, 1587)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Thomas", "Vines", getDate(5, 14, 1586)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "George", "Shute", getDate(7, 28, 1582)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Sidrach", "Spurstowe", getDate(1, 9, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "William", "Woodcock", getDate(3, 7, 1600)));
		allCustomers.add(new Customer(userId++, unitedKingdom, "Francis", "Simpson", getDate(7, 7, 1614)));
	}

	@Override
	public List<Customer> getAllCustomers() {
		return allCustomers;
	}

	public void setCountryService(CountryService countryService) {
		this.countryService = countryService;
	}

	public CountryService getCountryService() {
		if (countryService == null) {
			countryService = getCService(FacesContext.getCurrentInstance());
		}
		return countryService;
	}
	
	@Override
	public int getCustomerCount() {
		return allCustomers.size();
	}

	@Override
	public List<Customer> getCustomers(int start, int finish) {
		return allCustomers.subList(start, finish + 1);
	}

	@Override
	public List<Customer> getCustomers(int start, int finish, Comparator<Customer> comparator) {

		List<Customer> customerList = new ArrayList<Customer>(getAllCustomers());
		Collections.sort(customerList, comparator);

		return customerList.subList(start, finish + 1);
	}

	public Date getDate(int month, int day, int year) {
		TimeZone gmtTimeZone = TimeZone.getTimeZone("GMT");
		Calendar calendar = new GregorianCalendar(gmtTimeZone);
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.YEAR, year);

		return calendar.getTime();
	}
	
	protected CountryService getCService(FacesContext facesContext) {

		Application application = facesContext.getApplication();
		ELResolver elResolver = application.getELResolver();
		ELContext elContext = facesContext.getELContext();

		return (CountryService) elResolver.getValue(elContext, null, "countryService");
	}
}
