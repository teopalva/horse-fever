package it.polimi.provaFinale2013;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import it.polimi.provaFinale2013.exceptions.CantChangeException;
import it.polimi.provaFinale2013.exceptions.NoMoreMoneyInBankException;
import it.polimi.provaFinale2013.exceptions.NotEnoughMoneyException;
import it.polimi.provaFinale2013.model.Bank;
import it.polimi.provaFinale2013.model.Banknote;
import it.polimi.provaFinale2013.model.Wallet;

import java.rmi.RemoteException;
import java.util.List;

import org.junit.Test;

public class TestBanknotes {

	@Test
	public void testBanknote() {
		Banknote b0 = new Banknote(1000);
		assertEquals(1000, b0.getValue());
	}

	@Test
	public void testWallet() {
		Banknote b0 = new Banknote(100);
		Banknote b1 = new Banknote(500);
		Banknote b2 = new Banknote(1000);
		Wallet w = new Wallet(new Bank());
		w.putBanknotes(b0);
		w.putBanknotes(b1);
		w.putBanknotes(b2);
		assertEquals(1600, w.getValue());
	}

	@Test
	public void test0Bank() {
		Banknote b0 = new Banknote(100);
		Banknote b1 = new Banknote(500);
		Banknote b2 = new Banknote(1000);
		Bank bank = new Bank();
		bank.putBanknotes(b0);
		bank.putBanknotes(b1);
		bank.putBanknotes(b2);
		assertEquals(1600, bank.getValue());
	}

	@Test
	public void test1Bank() {
		Banknote b0 = new Banknote(100);
		Banknote b1 = new Banknote(500);
		Banknote b2 = new Banknote(1000);
		Bank bank = new Bank();
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b0);
		}
		bank.putBanknotes(b1);
		bank.putBanknotes(b2);
		try {
			bank.getBanknotes(101500);
		} catch (NotEnoughMoneyException e) {
		}
		assertEquals(0, bank.getValue());
	}

	@Test
	public void test0BankChange() {
		Banknote b0 = new Banknote(100);
		Banknote b1 = new Banknote(500);
		Banknote b2 = new Banknote(1000);
		Bank bank = new Bank();
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b0);
		}
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b1);
		}
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b2);
		}
		boolean ok = true;
		int value = 0;
		List<Banknote> banknotes = null;
		try {
			banknotes = bank.splitBanknote(b2);
		} catch (CantChangeException e) {
			assertTrue(false);
		}
		for (Banknote b : banknotes) {
			value += b.getValue();
			if (b.getValue() != 500) {
				ok = false;
			}
		}
		if (value != 1000) {
			ok = false;
		}
		assertTrue(ok);
	}

	@Test
	public void test0WalletChange() {
		Banknote b0 = new Banknote(100);
		Banknote b1 = new Banknote(500);
		Banknote b2 = new Banknote(1000);
		Bank bank = new Bank();
		Wallet wallet = new Wallet(bank);
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b0);
		}
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b1);
		}
		for (int i = 0; i < 1000; i++) {
			bank.putBanknotes(b2);
		}
		//for(int i=0; i<10; i++)
		wallet.putBanknotes(b2);
		try {
			try {
				try {
					wallet.getBanknotes(100);
				} catch (RemoteException e) {
					assertTrue(false);
				}
			} catch (NoMoreMoneyInBankException e) {
				assertTrue(false);
				System.out.printf("NoMoreMoneyInBankException");
			}
		} catch (NotEnoughMoneyException e) {
			assertTrue(false);
			System.out.printf("NotEnoughMoneyException");
		}
		System.out.printf("%d", wallet.getValue());
		assertEquals(wallet.getValue(), 900);
	}

}
