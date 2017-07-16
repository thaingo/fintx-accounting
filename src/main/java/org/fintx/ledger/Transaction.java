/**
 *  Copyright 2017 FinTx
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fintx.ledger;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.fintx.lang.Pair;
import org.fintx.ledger.Registration.Builder.RegistrationVerifer;

import lombok.Getter;

/**
 * @author bluecreator(qiang.x.wang@gmail.com)
 *
 */
@Getter
public class Transaction {
	private Transaction() {

	}

	private Voucher voucher;
	private ArrayList<Pair<String, BigDecimal>> creditEntrys;
	private ArrayList<Pair<String, BigDecimal>> debitEntrys;
	// control and buff should be a configuration not a function
	// private ArrayList controlCreditEntrys;
	// private ArrayList controlDebitEntrys;
	// private ArrayList buffCreditEntrys;
	// private ArrayList buffDebitEntrys;
	


	public static class Builder {
		private Transaction transaction = new Transaction();
		private Verifer<Transaction> verifer = null;
		private static final Verifer<Transaction> defaultVerifer=new TransactionVerifer();
		private Builder() {
		}
		public Builder associate(Voucher voucher) {
			
			return this;
		}
		public Builder credit(String acctNo, BigDecimal amt) {
			if(null==transaction.creditEntrys) {
				synchronized (transaction){
					transaction.creditEntrys=new ArrayList<Pair<String, BigDecimal>>(5);
				}
			}
			transaction.creditEntrys.add(new Pair<String, BigDecimal>(acctNo,amt));
			return this;
		}
		public Builder debit(String acctNo, BigDecimal amt) {
			return this;
		}
		public Builder transactionVerifer(TransactionVerifer verifer) {
			this.verifer = verifer;
			return this;
		}
		public Transaction build() {
			if (null == verifer) {
				verifer=defaultVerifer;
			}
			return transaction;
		};
		public static class TransactionVerifer implements Verifer<Transaction>{
			
			public boolean verify(Transaction txn) {
				return true;
			};

		}
	}

}
