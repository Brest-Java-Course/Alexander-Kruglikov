package com.brest.bank.soap;

import com.brest.bank.domain.BankDeposit;
import com.brest.bank.service.BankDepositService;
import com.brest.bank.service.BankDepositorService;

import com.brest.bank.domain.BankDepositor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.easymock.EasyMock.*;

import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.server.RequestCreators;
import org.springframework.ws.test.server.ResponseMatchers;

import org.springframework.xml.transform.StringSource;

import javax.xml.transform.Source;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-soap-Mock-test.xml"})
public class DepositSoapEndpointTest {

    private static final Logger LOGGER = LogManager.getLogger();
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    BankDepositService depositService;

    @Autowired
    BankDepositorService depositorService;

    @Autowired
    DepositSoapEndpoint depositSoapEndpoint;

    @Autowired
    ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @Before
    public void createClient() {
        depositSoapEndpoint = new DepositSoapEndpoint(depositService, depositorService);
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @After
    public void tearDown(){
        reset(depositService);
        reset(depositorService);
    }

    @Test
    public void testGetBankDeposits() throws Exception {
        LOGGER.debug("testGetBankDeposits() - run");

        expect(depositService.getBankDeposits()).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsRequest xmlns='http://bank.brest.com/soap'>" +
                "</getBankDepositsRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositsResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDeposits>" +
                            "<bankDeposit>" +
                                "<depositId>1</depositId>" +
                                "<depositName>depositName1</depositName>" +
                                "<depositMinTerm>12</depositMinTerm>" +
                                "<depositMinAmount>1000</depositMinAmount>" +
                                "<depositCurrency>usd</depositCurrency>" +
                                "<depositInterestRate>4</depositInterestRate>" +
                                "<depositAddConditions>conditions1</depositAddConditions>" +
                            "</bankDeposit>"+
                        "</bankDeposits>" +
                "</getBankDepositsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositors() - run");

        expect(depositorService.getBankDepositors()).andReturn(DataFixture.getExistDepositors());
        replay(depositorService);

        Source requestPayload = new StringSource(
                "<getBankDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "</getBankDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositorsResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDepositors>" +
                            "<bankDepositor>" +
                                "<depositorId>1</depositorId>" +
                                "<depositorName>depositorName1</depositorName>" +
                                "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                                "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                                "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                                "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                                "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                                "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                            "</bankDepositor>" +
                            "<bankDepositor>" +
                                "<depositorId>2</depositorId>" +
                                "<depositorName>depositorName1</depositorName>" +
                                "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                                "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                                "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                                "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                                "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                                "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                            "</bankDepositor>" +
                        "</bankDepositors>" +
                "</getBankDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testGetBankDepositById() throws Exception {
        LOGGER.debug("testGetBankDepositById() - run");

        expect(depositService.getBankDepositById(1L)).andReturn(DataFixture.getExistDeposit(1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<ns2:getBankDepositByIdRequest xmlns:ns2='http://bank.brest.com/soap'>" +
                    "<ns2:depositId>1</ns2:depositId>" +
                "</ns2:getBankDepositByIdRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByIdResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDeposit>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>depositName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                        "</ns2:bankDeposit>" +
                "</ns2:getBankDepositByIdResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositByName() throws Exception {
        LOGGER.debug("testGetBankDepositByName() - run");

        expect(depositService.getBankDepositByName("depositName1")).andReturn(DataFixture.getExistDeposit(1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByNameRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositName>depositName1</depositName>" +
                        "</getBankDepositByNameRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositByNameResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDeposit>" +
                            "<depositId>1</depositId>" +
                            "<depositName>depositName1</depositName>" +
                            "<depositMinTerm>12</depositMinTerm>" +
                            "<depositMinAmount>1000</depositMinAmount>" +
                            "<depositCurrency>usd</depositCurrency>" +
                            "<depositInterestRate>4</depositInterestRate>" +
                            "<depositAddConditions>conditions1</depositAddConditions>" +
                        "</bankDeposit>" +
                "</getBankDepositByNameResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testInvalidRequestGetBankDepositById() {
        LOGGER.debug("testInvalidGetBankDepositById() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositByIdRequest xmlns='http://bank.brest.com/soap' " +
                        "depositId='1'/>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">java.lang.NullPointerException</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testInvalidIdGetBankDepositById() {
        LOGGER.debug("testInvalidGetBankDepositById() - run");

        depositService.getBankDepositById(-1L);
        expectLastCall().andThrow(new IllegalArgumentException("The parameter can not be NULL"));
        replay(depositService);


        Source requestPayload = new StringSource(
                "<getBankDepositByIdRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>-1</depositId>" +
                "</getBankDepositByIdRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsByCurrency() throws Exception {
        LOGGER.debug("testGetBankDepositsByCurrency() - run");

        expect(depositService.getBankDepositsByCurrency("usd")).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsByCurrencyRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositCurrency>usd</depositCurrency>" +
                "</getBankDepositsByCurrencyRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositsByCurrencyResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDeposits>" +
                            "<bankDeposit>" +
                                "<depositId>1</depositId>" +
                                "<depositName>depositName1</depositName>" +
                                "<depositMinTerm>12</depositMinTerm>" +
                                "<depositMinAmount>1000</depositMinAmount>" +
                                "<depositCurrency>usd</depositCurrency>" +
                                "<depositInterestRate>4</depositInterestRate>" +
                                "<depositAddConditions>conditions1</depositAddConditions>" +
                            "</bankDeposit>"+
                        "</bankDeposits>" +
                "</getBankDepositsByCurrencyResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsByInterestRate() throws Exception {
        LOGGER.debug("testGetBankDepositsByInterestRate() - run");

        expect(depositService.getBankDepositsByInterestRate(4)).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsByInterestRateRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositInterestRate>4</depositInterestRate>" +
                "</getBankDepositsByInterestRateRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositsByInterestRateResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDeposits>" +
                            "<bankDeposit>" +
                                "<depositId>1</depositId>" +
                                "<depositName>depositName1</depositName>" +
                                "<depositMinTerm>12</depositMinTerm>" +
                                "<depositMinAmount>1000</depositMinAmount>" +
                                "<depositCurrency>usd</depositCurrency>" +
                                "<depositInterestRate>4</depositInterestRate>" +
                                "<depositAddConditions>conditions1</depositAddConditions>" +
                            "</bankDeposit>"+
                        "</bankDeposits>" +
                "</getBankDepositsByInterestRateResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsFromToMinTerm() throws Exception {
        LOGGER.debug("testGetBankDepositsFomToMinTerm() - run");

        expect(depositService.getBankDepositsFromToMinTerm(10,12)).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToMinTermRequest xmlns='http://bank.brest.com/soap'>" +
                        "<fromTerm>10</fromTerm>" +
                        "<toTerm>12</toTerm>" +
                "</getBankDepositsFromToMinTermRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositsFromToMinTermResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDeposits>" +
                            "<bankDeposit>" +
                                "<depositId>1</depositId>" +
                                "<depositName>depositName1</depositName>" +
                                "<depositMinTerm>12</depositMinTerm>" +
                                "<depositMinAmount>1000</depositMinAmount>" +
                                "<depositCurrency>usd</depositCurrency>" +
                                "<depositInterestRate>4</depositInterestRate>" +
                                "<depositAddConditions>conditions1</depositAddConditions>" +
                            "</bankDeposit>"+
                        "</bankDeposits>" +
                "</getBankDepositsFromToMinTermResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsInvalidFromToMinTerm() throws Exception {
        LOGGER.debug("testGetBankDepositsInvalidFomToMinTerm() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToMinTermRequest xmlns='http://bank.brest.com/soap'>" +
                        "<fromTerm>12</fromTerm>" +
                        "<toTerm>11</toTerm>" +
                "</getBankDepositsFromToMinTermRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The first parameter should be less than the second</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsNullFromToMinTerm() throws Exception {
        LOGGER.debug("testGetBankDepositsNullFomToMinTerm() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToMinTermRequest xmlns='http://bank.brest.com/soap'>" +
                        "<fromTerm>null</fromTerm>" +
                        "<toTerm>12</toTerm>" +
                "</getBankDepositsFromToMinTermRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromNullToMinTerm() throws Exception {
        LOGGER.debug("testGetBankDepositsFromNullToMinTerm() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToMinTermRequest xmlns='http://bank.brest.com/soap'>" +
                        "<fromTerm>10</fromTerm>" +
                        "<toTerm>null</toTerm>" +
                "</getBankDepositsFromToMinTermRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromToInterestRate() throws Exception {
        LOGGER.debug("testGetBankDepositsFomToInterestRate() - run");

        expect(depositService.getBankDepositsFromToInterestRate(2,4)).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToInterestRateRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startRate>2</startRate>" +
                        "<endRate>4</endRate>" +
                "</getBankDepositsFromToInterestRateRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsFromToInterestRateResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDeposits>" +
                            "<ns2:bankDeposit>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "</ns2:bankDeposit>" +
                        "</ns2:bankDeposits>" +
                "</ns2:getBankDepositsFromToInterestRateResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsInvalidFromToInterestRate() throws Exception {
        LOGGER.debug("testGetBankDepositsInvalidFomToInterestRate() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToInterestRateRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startRate>4</startRate>" +
                        "<endRate>2</endRate>" +
                "</getBankDepositsFromToInterestRateRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The first parameter should be less than the second</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsNullFromToInterestRate() throws Exception {
        LOGGER.debug("testGetBankDepositsNullFomToInterestRate() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToInterestRateRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startRate>null</startRate>" +
                        "<endRate>4</endRate>" +
                "</getBankDepositsFromToInterestRateRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromNullToInterestRate() throws Exception {
        LOGGER.debug("testGetBankDepositsFromNullToInterestRate() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToInterestRateRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startRate>2</startRate>" +
                        "<endRate>null</endRate>" +
                "</getBankDepositsFromToInterestRateRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromToDateDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsFomToDateDeposit() - run");

        expect(depositService.getBankDepositsFromToDateDeposit(dateFormat.parse("2015-01-01"),
                dateFormat.parse("2015-02-02"))).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositsFromToDateDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsFromToDateDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDeposits>" +
                            "<ns2:bankDeposit>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "</ns2:bankDeposit>" +
                        "</ns2:bankDeposits>" +
                "</ns2:getBankDepositsFromToDateDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsInvalidFromToDateDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsInvalidFomToDateDeposit() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-02-02</startDate>" +
                        "<endDate>2015-01-01</endDate>" +
                "</getBankDepositsFromToDateDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The first parameter should be less than the second</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsNullFromToDateDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsNullFomToDateDeposit() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>null</startDate>" +
                        "<endDate>2015-01-01</endDate>" +
                "</getBankDepositsFromToDateDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromNullToDateDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsNullFomToDateDeposit() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>null</endDate>" +
                "</getBankDepositsFromToDateDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromToDateReturnDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsFomToDateReturnDeposit() - run");

        expect(depositService.getBankDepositsFromToDateReturnDeposit(dateFormat.parse("2015-01-01"),
                dateFormat.parse("2015-02-02"))).andReturn(DataFixture.getExistDeposits());
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateReturnDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositsFromToDateReturnDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsFromToDateReturnDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDeposits>" +
                            "<ns2:bankDeposit>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "</ns2:bankDeposit>" +
                        "</ns2:bankDeposits>" +
                "</ns2:getBankDepositsFromToDateReturnDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsInvalidFromToDateReturnDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsInvalidFomToDateReturnDeposit() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateReturnDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-02-02</startDate>" +
                        "<endDate>2015-01-01</endDate>" +
                "</getBankDepositsFromToDateReturnDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The first parameter should be less than the second</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsNullFromToDateReturnDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsNullFomToDateReturnDeposit() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateReturnDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>null</startDate>" +
                        "<endDate>2015-01-01</endDate>" +
                "</getBankDepositsFromToDateReturnDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositsFromNullToDateReturnDeposit() throws Exception {
        LOGGER.debug("testGetBankDepositsNullFomToDateReturnDeposit() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateReturnDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>null</endDate>" +
                "</getBankDepositsFromToDateReturnDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositByNameWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByNameWithDepositors() - run");

        expect(depositService.getBankDepositByNameWithDepositors("depositName1"))
                .andReturn(DataFixture.getExistDepositAllDepositors(1L,1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByNameWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositName>depositName1</depositName>" +
                "</getBankDepositByNameWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByNameWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositReport>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>depositName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "<ns2:depositorCount>1</ns2:depositorCount>" +
                            "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                            "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                            "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                "</ns2:getBankDepositByNameWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositByNameFromToDateDepositWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByNameFromToDateDepositWithDepositors() - run");

        expect(depositService.getBankDepositByNameFromToDateDepositWithDepositors("depositName1",
                dateFormat.parse("2015-01-01"),dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistDepositAllDepositors(1L,1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByNameFromToDateDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositName>depositName1</depositName>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositByNameFromToDateDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByNameFromToDateDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositReport>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>depositName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "<ns2:depositorCount>1</ns2:depositorCount>" +
                            "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                            "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                            "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                "</ns2:getBankDepositByNameFromToDateDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositByNameInvalidFromToDateDepositWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByNameInvalidFromToDateDepositWithDepositors() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositByNameFromToDateDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositName>depositName1</depositName>" +
                        "<startDate>2015-02-01</startDate>" +
                        "<endDate>2015-01-01</endDate>" +
                "</getBankDepositByNameFromToDateDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The first parameter should be less than the second</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositByNameFromToDateReturnDepositWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByNameFromToDateReturnDepositWithDepositors() - run");

        expect(depositService.getBankDepositByNameFromToDateReturnDepositWithDepositors("depositName1",
                dateFormat.parse("2015-01-01"),dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistDepositAllDepositors(1L,1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByNameFromToDateReturnDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositName>depositName1</depositName>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositByNameFromToDateReturnDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByNameFromToDateReturnDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositReport>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>depositName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "<ns2:depositorCount>1</ns2:depositorCount>" +
                            "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                            "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                            "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                "</ns2:getBankDepositByNameFromToDateReturnDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService   );
    }

    @Test
    public void testGetBankDepositByNameInvalidFromToDateReturnDepositWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByNameInvalidFromToDateReturnDepositWithDepositors() - run");

        Source requestPayload = new StringSource(
                "<getBankDepositByNameFromToDateReturnDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositName>depositName1</depositName>" +
                        "<startDate>2015-02-01</startDate>" +
                        "<endDate>2015-01-01</endDate>" +
                "</getBankDepositByNameFromToDateReturnDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The first parameter should be less than the second</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }

    @Test
    public void testGetBankDepositByIdWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByIdWithDepositors() - run");

        expect(depositService.getBankDepositByIdWithDepositors(1L))
                .andReturn(DataFixture.getExistDepositAllDepositors(1L,1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByIdWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                "</getBankDepositByIdWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByIdWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositReport>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>depositName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "<ns2:depositorCount>1</ns2:depositorCount>" +
                            "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                            "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                            "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                "</ns2:getBankDepositByIdWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositByIdFromToDateDepositWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByIdFromToDateDepositWithDepositors() - run");

        expect(depositService.getBankDepositByIdFromToDateDepositWithDepositors(1L,
                dateFormat.parse("2015-01-01"),dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistDepositAllDepositors(1L,1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByIdFromToDateDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositByIdFromToDateDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByIdFromToDateDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositReport>" +
                        "<ns2:depositId>1</ns2:depositId>" +
                        "<ns2:depositName>depositName1</ns2:depositName>" +
                        "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                        "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                        "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                        "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                        "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                        "<ns2:depositorCount>1</ns2:depositorCount>" +
                        "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                        "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                        "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                "</ns2:getBankDepositByIdFromToDateDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositByIdFromToDateReturnDepositWithDepositors() throws Exception {
        LOGGER.debug("testGetBankDepositByIdFromToDateReturnDepositWithDepositors() - run");

        expect(depositService.getBankDepositByIdFromToDateReturnDepositWithDepositors(1L,
                dateFormat.parse("2015-01-01"),dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistDepositAllDepositors(1L,1L));
        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositByIdFromToDateReturnDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositByIdFromToDateReturnDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositByIdFromToDateReturnDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositReport>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>depositName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                            "<ns2:depositorCount>1</ns2:depositorCount>" +
                            "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                            "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                            "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                "</ns2:getBankDepositByIdFromToDateReturnDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

   @Test
   public void testGetBankDepositsWithDepositors() throws ParseException{
       LOGGER.debug("testGetBankDepositsWithDepositors - run");

       expect(depositService.getBankDepositsWithDepositors())
               .andReturn(DataFixture.getExistAllDepositsAllDepositors());

       replay(depositService);

       Source requestPayload = new StringSource(
               "<getBankDepositsWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
               "</getBankDepositsWithDepositorsRequest>");

       Source responsePayload = new StringSource(
               "<ns2:getBankDepositsWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                       "<ns2:bankDepositsReport>" +
                            "<ns2:bankDepositReport>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                                "<ns2:depositorCount>1</ns2:depositorCount>" +
                                "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                                "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                                "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                            "</ns2:bankDepositReport>" +
                       "</ns2:bankDepositsReport>" +
               "</ns2:getBankDepositsWithDepositorsResponse>");

       RequestCreator creator = RequestCreators.withPayload(requestPayload);

       this.mockClient
               .sendRequest(creator)
               .andExpect(ResponseMatchers.payload(responsePayload));

       verify(depositService);
   }

    @Test
    public void testGetBankDepositsFromToDateDepositWithDepositors() throws ParseException{
        LOGGER.debug("testGetBankDepositsFromToDateDepositWithDepositors - run");

        expect(depositService.getBankDepositsFromToDateDepositWithDepositors(dateFormat.parse("2015-01-01"),
                dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistAllDepositsAllDepositors());

        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositsFromToDateDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsFromToDateDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositsReport>" +
                        "<ns2:bankDepositReport>" +
                        "<ns2:depositId>1</ns2:depositId>" +
                        "<ns2:depositName>depositName1</ns2:depositName>" +
                        "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                        "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                        "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                        "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                        "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                        "<ns2:depositorCount>1</ns2:depositorCount>" +
                        "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                        "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                        "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                        "</ns2:bankDepositReport>" +
                        "</ns2:bankDepositsReport>" +
                "</ns2:getBankDepositsFromToDateDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsFromToDateReturnDepositWithDepositors() throws ParseException{
        LOGGER.debug("testGetBankDepositsFromToDateReturnDepositWithDepositors - run");

        expect(depositService.getBankDepositsFromToDateReturnDepositWithDepositors(dateFormat.parse("2015-01-01"),
                dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistAllDepositsAllDepositors());

        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsFromToDateReturnDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositsFromToDateReturnDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsFromToDateReturnDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositsReport>" +
                            "<ns2:bankDepositReport>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                                "<ns2:depositorCount>1</ns2:depositorCount>" +
                                "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                                "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                                "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                            "</ns2:bankDepositReport>" +
                        "</ns2:bankDepositsReport>" +
                "</ns2:getBankDepositsFromToDateReturnDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsByCurrencyWithDepositors() throws ParseException{
        LOGGER.debug("testGetBankDepositsByCurrencyWithDepositors - run");

        expect(depositService.getBankDepositsByCurrencyWithDepositors("usd"))
                .andReturn(DataFixture.getExistAllDepositsAllDepositors());

        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsByCurrencyWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositCurrency>usd</depositCurrency>" +
                "</getBankDepositsByCurrencyWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsByCurrencyWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositsReport>" +
                            "<ns2:bankDepositReport>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                                "<ns2:depositorCount>1</ns2:depositorCount>" +
                                "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                                "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                                "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                            "</ns2:bankDepositReport>" +
                        "</ns2:bankDepositsReport>" +
                "</ns2:getBankDepositsByCurrencyWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsByCurrencyFromToDateDepositWithDepositors() throws ParseException{
        LOGGER.debug("testGetBankDepositsByCurrencyFromToDateDepositWithDepositors - run");

        expect(depositService.getBankDepositsByCurrencyFromToDateDepositWithDepositors("usd",
                dateFormat.parse("2015-01-01"),dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistAllDepositsAllDepositors());

        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsByCurrencyFromToDateDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositCurrency>usd</depositCurrency>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositsByCurrencyFromToDateDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsByCurrencyFromToDateDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositsReport>" +
                            "<ns2:bankDepositReport>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                                "<ns2:depositorCount>1</ns2:depositorCount>" +
                                "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                                "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                                "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                            "</ns2:bankDepositReport>" +
                        "</ns2:bankDepositsReport>" +
                "</ns2:getBankDepositsByCurrencyFromToDateDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testGetBankDepositsByCurrencyFromToDateReturnDepositWithDepositors() throws ParseException{
        LOGGER.debug("testGetBankDepositsByCurrencyFromToDateReturnDepositWithDepositors - run");

        expect(depositService.getBankDepositsByCurrencyFromToDateReturnDepositWithDepositors("usd",
                dateFormat.parse("2015-01-01"),dateFormat.parse("2015-02-02")))
                .andReturn(DataFixture.getExistAllDepositsAllDepositors());

        replay(depositService);

        Source requestPayload = new StringSource(
                "<getBankDepositsByCurrencyFromToDateReturnDepositWithDepositorsRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositCurrency>usd</depositCurrency>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositsByCurrencyFromToDateReturnDepositWithDepositorsRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositsByCurrencyFromToDateReturnDepositWithDepositorsResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositsReport>" +
                            "<ns2:bankDepositReport>" +
                                "<ns2:depositId>1</ns2:depositId>" +
                                "<ns2:depositName>depositName1</ns2:depositName>" +
                                "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                                "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                                "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                                "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                                "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                                "<ns2:depositorCount>1</ns2:depositorCount>" +
                                "<ns2:depositorAmountSum>1000</ns2:depositorAmountSum>" +
                                "<ns2:depositorAmountPlusSum>100</ns2:depositorAmountPlusSum>" +
                                "<ns2:depositorAmountMinusSum>100</ns2:depositorAmountMinusSum>" +
                            "</ns2:bankDepositReport>" +
                        "</ns2:bankDepositsReport>" +
                 "</ns2:getBankDepositsByCurrencyFromToDateReturnDepositWithDepositorsResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testAddBankDeposit() throws Exception {
        LOGGER.debug("testAddBankDeposit() - run");

        depositService.addBankDeposit(anyObject(BankDeposit.class));
        expectLastCall();

        expect(depositService.getBankDepositByName("depositName1"))
                .andReturn(DataFixture.getExistDeposit(1L));

        replay(depositService);

        Source requestPayload = new StringSource(
                "<addBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<bankDeposit>" +
                            "<depositId></depositId>" +
                            "<depositName>depositName1</depositName>" +
                            "<depositMinTerm>12</depositMinTerm>" +
                            "<depositMinAmount>1000</depositMinAmount>" +
                            "<depositCurrency>usd</depositCurrency>" +
                            "<depositInterestRate>4</depositInterestRate>" +
                            "<depositAddConditions>conditions1</depositAddConditions>" +
                        "</bankDeposit>" +
                "</addBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<addBankDepositResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDeposit>" +
                            "<depositId>1</depositId>" +
                            "<depositName>depositName1</depositName>" +
                            "<depositMinTerm>12</depositMinTerm>" +
                            "<depositMinAmount>1000</depositMinAmount>" +
                            "<depositCurrency>usd</depositCurrency>" +
                            "<depositInterestRate>4</depositInterestRate>" +
                            "<depositAddConditions>conditions1</depositAddConditions>" +
                        "</bankDeposit>" +
                "</addBankDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testRemoveBankDeposit(){
        LOGGER.debug("testRemoveBankDeposit() - run");

        expect(depositService.getBankDepositById(1L)).andReturn(DataFixture.getExistDeposit(1L));

        depositService.deleteBankDeposit(1L);
        expectLastCall();

        expect(depositService.getBankDepositById(1L)).andReturn(null);

        replay(depositService);

        Source requestPayload = new StringSource(
                "<deleteBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                "</deleteBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:deleteBankDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:result>Bank Deposit removed</ns2:result>" +
                "</ns2:deleteBankDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testRemoveEmptyBankDeposit(){
        LOGGER.debug("testRemoveEmptyBankDeposit() - run");

        expect(depositService.getBankDepositById(1L)).andReturn(null);

        replay(depositService);

        Source requestPayload = new StringSource(
                "<deleteBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                "</deleteBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:deleteBankDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:result>In the database there is no Deposit with such parameters</ns2:result>" +
                "</ns2:deleteBankDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testInvalidRemovedBankDeposit(){
        LOGGER.debug("testInvalidRemovedBankDeposit() - run");

        expect(depositService.getBankDepositById(1L)).andReturn(DataFixture.getExistDeposit(1L));

        depositService.deleteBankDeposit(1L);
        expectLastCall();

        expect(depositService.getBankDepositById(1L)).andReturn(DataFixture.getExistDeposit(1L));

        replay(depositService);

        Source requestPayload = new StringSource(
                "<deleteBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                "</deleteBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:deleteBankDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:result>Bank Deposit don't removed</ns2:result>" +
                "</ns2:deleteBankDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testRemovedNullIdBankDeposit(){
        LOGGER.debug("testRemovedNullIdBankDeposit() - run");

        Long depositId = null;

        Source requestPayload = new StringSource(
                "<deleteBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>" +
                        depositId +
                        "</depositId>" +
                "</deleteBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

    }

    @Test
    public void testUpdateBankDeposit() throws Exception {
        LOGGER.debug("testUpdateBankDeposit() - run");

        BankDeposit updateDeposit = DataFixture.getExistDeposit(1L);
        updateDeposit.setDepositName("updateName1");

        depositService.updateBankDeposit(anyObject(BankDeposit.class));
        expectLastCall();

        expect(depositService.getBankDepositById(1L)).andReturn(updateDeposit);

        replay(depositService);

        Source requestPayload = new StringSource(
                "<updateBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<bankDeposit>" +
                            "<depositId>1</depositId>" +
                            "<depositName>updateName1</depositName>" +
                            "<depositMinTerm>12</depositMinTerm>" +
                            "<depositMinAmount>1000</depositMinAmount>" +
                            "<depositCurrency>usd</depositCurrency>" +
                            "<depositInterestRate>4</depositInterestRate>" +
                            "<depositAddConditions>conditions1</depositAddConditions>" +
                        "</bankDeposit>" +
                "</updateBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:updateBankDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDeposit>" +
                            "<ns2:depositId>1</ns2:depositId>" +
                            "<ns2:depositName>updateName1</ns2:depositName>" +
                            "<ns2:depositMinTerm>12</ns2:depositMinTerm>" +
                            "<ns2:depositMinAmount>1000</ns2:depositMinAmount>" +
                            "<ns2:depositCurrency>usd</ns2:depositCurrency>" +
                            "<ns2:depositInterestRate>4</ns2:depositInterestRate>" +
                            "<ns2:depositAddConditions>conditions1</ns2:depositAddConditions>" +
                        "</ns2:bankDeposit>" +
                "</ns2:updateBankDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositService);
    }

    @Test
    public void testUpdateBankDepositNullId() throws Exception {
        LOGGER.debug("testUpdateBankDepositNullId() - run");

        Source requestPayload = new StringSource(
                "<updateBankDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<bankDeposit>" +
                            "<depositId></depositId>" +
                            "<depositName>updateName1</depositName>" +
                            "<depositMinTerm>12</depositMinTerm>" +
                            "<depositMinAmount>1000</depositMinAmount>" +
                            "<depositCurrency>usd</depositCurrency>" +
                            "<depositInterestRate>4</depositInterestRate>" +
                            "<depositAddConditions>conditions1</depositAddConditions>" +
                        "</bankDeposit>" +
                "</updateBankDepositRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL- depositId</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }


    @Test
    public void testGetBankDepositorById() throws java.text.ParseException{
        LOGGER.debug("testGetBankDepositorById() - run");

        expect(depositorService.getBankDepositorById(1L)).andReturn(DataFixture.getExistDepositor(1L));
        replay(depositorService);

        Source requestPayload = new StringSource(
                "<getBankDepositorByIdRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositorId>1</depositorId>" +
                "</getBankDepositorByIdRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositorByIdResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDepositor>" +
                            "<depositorId>1</depositorId>" +
                            "<depositorName>depositorName1</depositorName>" +
                            "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                            "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                            "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                            "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                            "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                            "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                        "</bankDepositor>" +
                "</getBankDepositorByIdResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testGetBankDepositorByIdDeposit() throws ParseException{
        LOGGER.debug("testGetBankDepositorById() - run");

        expect(depositorService.getBankDepositorByIdDeposit(1L)).andReturn(DataFixture.getExistDepositors());
        replay(depositorService);

        Source requestPayload = new StringSource(
                "<getBankDepositorByIdDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                "</getBankDepositorByIdDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositorByIdDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositors>" +
                            "<ns2:bankDepositor>" +
                                "<ns2:depositorId>2</ns2:depositorId>" +
                                "<ns2:depositorName>depositorName1</ns2:depositorName>" +
                                "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                                "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                                "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                                "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                                "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                                "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                            "</ns2:bankDepositor>" +
                            "<ns2:bankDepositor>" +
                                "<ns2:depositorId>1</ns2:depositorId>" +
                                "<ns2:depositorName>depositorName1</ns2:depositorName>" +
                                "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                                "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                                "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                                "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                                "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                                "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                            "</ns2:bankDepositor>" +
                        "</ns2:bankDepositors>" +
                "</ns2:getBankDepositorByIdDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testGetBankDepositorsFromToDateDeposit() throws ParseException{
        LOGGER.debug("testGetBankDepositorsFromToDateDeposit() - run");

        expect(depositorService.getBankDepositorsFromToDateDeposit(dateFormat.parse("2015-01-01"),
                dateFormat.parse("2015-02-02"))).andReturn(DataFixture.getExistDepositors());
        replay(depositorService);

        Source requestPayload = new StringSource(
                "<getBankDepositorsFromToDateDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-01-01</startDate>" +
                        "<endDate>2015-02-02</endDate>" +
                "</getBankDepositorsFromToDateDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositorsFromToDateDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositors>" +
                            "<ns2:bankDepositor>" +
                                "<ns2:depositorId>2</ns2:depositorId>" +
                                "<ns2:depositorName>depositorName1</ns2:depositorName>" +
                                "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                                "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                                "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                                "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                                "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                                "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                            "</ns2:bankDepositor>" +
                            "<ns2:bankDepositor>" +
                                "<ns2:depositorId>1</ns2:depositorId>" +
                                "<ns2:depositorName>depositorName1</ns2:depositorName>" +
                                "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                                "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                                "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                                "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                                "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                                "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                            "</ns2:bankDepositor>" +
                        "</ns2:bankDepositors>" +
                "</ns2:getBankDepositorsFromToDateDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testGetBankDepositorsFromToDateReturnDeposit() throws ParseException{
        LOGGER.debug("testGetBankDepositorsFromToDateReturnDeposit() - run");

        expect(depositorService.getBankDepositorsFromToDateReturnDeposit(dateFormat.parse("2015-08-01"),
                dateFormat.parse("2015-09-09"))).andReturn(DataFixture.getExistDepositors());
        replay(depositorService);

        Source requestPayload = new StringSource(
                "<getBankDepositorsFromToDateReturnDepositRequest xmlns='http://bank.brest.com/soap'>" +
                        "<startDate>2015-08-01</startDate>" +
                        "<endDate>2015-09-09</endDate>" +
                "</getBankDepositorsFromToDateReturnDepositRequest>");

        Source responsePayload = new StringSource(
                "<ns2:getBankDepositorsFromToDateReturnDepositResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositors>" +
                            "<ns2:bankDepositor>" +
                                "<ns2:depositorId>2</ns2:depositorId>" +
                                "<ns2:depositorName>depositorName1</ns2:depositorName>" +
                                "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                                "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                                "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                                "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                                "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                                "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                            "</ns2:bankDepositor>" +
                            "<ns2:bankDepositor>" +
                                "<ns2:depositorId>1</ns2:depositorId>" +
                                "<ns2:depositorName>depositorName1</ns2:depositorName>" +
                                "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                                "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                                "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                                "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                                "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                                "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                            "</ns2:bankDepositor>" +
                        "</ns2:bankDepositors>" +
                "</ns2:getBankDepositorsFromToDateReturnDepositResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testGetBankDepositorByName() throws java.text.ParseException{
        LOGGER.debug("testGetBankDepositorByName() - run");

        expect(depositorService.getBankDepositorByName("depositorName1")).andReturn(DataFixture.getExistDepositor(1L));
        replay(depositorService);

        Source requestPayload = new StringSource(
                "<getBankDepositorByNameRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositorName>depositorName1</depositorName>" +
                "</getBankDepositorByNameRequest>");

        Source responsePayload = new StringSource(
                "<getBankDepositorByNameResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDepositor>" +
                            "<depositorId>1</depositorId>" +
                            "<depositorName>depositorName1</depositorName>" +
                            "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                            "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                            "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                            "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                            "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                            "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                        "</bankDepositor>" +
                "</getBankDepositorByNameResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testAddBankDepositor() throws Exception {
        LOGGER.debug("testAddBankDepositor() - run");

        depositorService.addBankDepositor(anyLong(),anyObject(BankDepositor.class));
        expectLastCall();

        expect(depositorService.getBankDepositorByName("depositorName1"))
                .andReturn(DataFixture.getExistDepositor(1L));

        replay(depositorService);

        Source requestPayload = new StringSource(
                "<addBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositId>1</depositId>" +
                        "<bankDepositor>" +
                            "<depositorId></depositorId>" +
                            "<depositorName>depositorName1</depositorName>" +
                            "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                            "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                            "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                            "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                            "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                            "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                        "</bankDepositor>" +
                "</addBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<addBankDepositorResponse xmlns=\"http://bank.brest.com/soap\">" +
                        "<bankDepositor>" +
                            "<depositorId>1</depositorId>" +
                            "<depositorName>depositorName1</depositorName>" +
                            "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                            "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                            "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                            "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                            "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                            "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                        "</bankDepositor>" +
                "</addBankDepositorResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testRemoveBankDepositor() throws ParseException{
        LOGGER.debug("testRemoveBankDepositor() - run");

        expect(depositorService.getBankDepositorById(1L)).andReturn(DataFixture.getExistDepositor(1L));

        depositorService.removeBankDepositor(1L);
        expectLastCall();

        expect(depositorService.getBankDepositorById(1L)).andReturn(null);

        replay(depositorService);

        Source requestPayload = new StringSource(
                "<deleteBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositorId>1</depositorId>" +
                "</deleteBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<ns2:deleteBankDepositorResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:result>Bank Depositor with ID=1 - removed</ns2:result>" +
                "</ns2:deleteBankDepositorResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testRemoveEmptyBankDepositor(){
        LOGGER.debug("testRemoveEmptyBankDepositor() - run");

        expect(depositorService.getBankDepositorById(1L)).andReturn(null);

        replay(depositorService);

        Source requestPayload = new StringSource(
                "<deleteBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositorId>1</depositorId>" +
                "</deleteBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<ns2:deleteBankDepositorResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:result>In the database there is no Deposit with such parameters</ns2:result>" +
                "</ns2:deleteBankDepositorResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testInvalidRemovedBankDepositor() throws ParseException{
        LOGGER.debug("testInvalidRemovedBankDepositor() - run");

        expect(depositorService.getBankDepositorById(1L)).andReturn(DataFixture.getExistDepositor(1L));

        depositorService.removeBankDepositor(1L);
        expectLastCall();

        expect(depositorService.getBankDepositorById(1L)).andReturn(DataFixture.getExistDepositor(1L));

        replay(depositorService);

        Source requestPayload = new StringSource(
                "<deleteBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositorId>1</depositorId>" +
                "</deleteBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<ns2:deleteBankDepositorResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:result>Bank Depositor don't removed</ns2:result>" +
                "</ns2:deleteBankDepositorResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testRemovedNullIdBankDepositor(){
        LOGGER.debug("testRemovedNullIdBankDepositor() - run");

        Long depositorId = null;

        Source requestPayload = new StringSource(
                "<deleteBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<depositorId>" +
                        depositorId +
                        "</depositorId>" +
                "</deleteBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

    }

    @Test
    public void testUpdateBankDepositor() throws Exception {
        LOGGER.debug("testUpdateBankDepositor() - run");

        BankDepositor updateDepositor = DataFixture.getExistDepositor(1L);
        updateDepositor.setDepositorName("updateName1");

        depositorService.updateBankDepositor(anyObject(BankDepositor.class));
        expectLastCall();

        expect(depositorService.getBankDepositorById(1L)).andReturn(updateDepositor);

        replay(depositorService);

        Source requestPayload = new StringSource(
                "<updateBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<bankDepositor>" +
                        "<depositorId>1</depositorId>" +
                        "<depositorName>updateName1</depositorName>" +
                        "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                        "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                        "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                        "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                        "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                        "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                        "</bankDepositor>" +
                "</updateBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<ns2:updateBankDepositorResponse xmlns:ns2=\"http://bank.brest.com/soap\">" +
                        "<ns2:bankDepositor>" +
                            "<ns2:depositorId>1</ns2:depositorId>" +
                            "<ns2:depositorName>updateName1</ns2:depositorName>" +
                            "<ns2:depositorDateDeposit>2015-01-01Z</ns2:depositorDateDeposit>" +
                            "<ns2:depositorAmountDeposit>1000</ns2:depositorAmountDeposit>" +
                            "<ns2:depositorAmountPlusDeposit>100</ns2:depositorAmountPlusDeposit>" +
                            "<ns2:depositorAmountMinusDeposit>100</ns2:depositorAmountMinusDeposit>" +
                            "<ns2:depositorDateReturnDeposit>2015-09-09Z</ns2:depositorDateReturnDeposit>" +
                            "<ns2:depositorMarkReturnDeposit>0</ns2:depositorMarkReturnDeposit>" +
                        "</ns2:bankDepositor>" +
                "</ns2:updateBankDepositorResponse>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));

        verify(depositorService);
    }

    @Test
    public void testUpdateBankDepositorNullId() throws Exception {
        LOGGER.debug("testUpdateBankDepositorNullId() - run");

        Source requestPayload = new StringSource(
                "<updateBankDepositorRequest xmlns='http://bank.brest.com/soap'>" +
                        "<bankDepositor>" +
                            "<depositorId></depositorId>" +
                            "<depositorName>depositorName1</depositorName>" +
                            "<depositorDateDeposit>2015-01-01Z</depositorDateDeposit>" +
                            "<depositorAmountDeposit>1000</depositorAmountDeposit>" +
                            "<depositorAmountPlusDeposit>100</depositorAmountPlusDeposit>" +
                            "<depositorAmountMinusDeposit>100</depositorAmountMinusDeposit>" +
                            "<depositorDateReturnDeposit>2015-09-09Z</depositorDateReturnDeposit>" +
                            "<depositorMarkReturnDeposit>0</depositorMarkReturnDeposit>" +
                        "</bankDepositor>" +
                "</updateBankDepositorRequest>");

        Source responsePayload = new StringSource(
                "<SOAP-ENV:Fault xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                        "<faultcode>SOAP-ENV:Server</faultcode>" +
                        "<faultstring xml:lang=\"en\">The parameter can not be NULL- depositorId</faultstring>" +
                "</SOAP-ENV:Fault>");

        RequestCreator creator = RequestCreators.withPayload(requestPayload);

        this.mockClient
                .sendRequest(creator)
                .andExpect(ResponseMatchers.payload(responsePayload));
    }
}
