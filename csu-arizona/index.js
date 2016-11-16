'use strict'

const co = require('co')
const fetch = require('node-fetch')
const _ = require('lodash')

const BASE_URL = 'https://dragons-tst.kuali.co/fin/coa/api/v1/reference'
const AUTH_HEADER = {
	Authorization: `Bearer ${process.env.AUTH_TOKEN}`,
}

co(function * main() {
	const subFundGroups = yield fetch(`${BASE_URL}/sfgr?fundGroupCode=AE`, {
		headers: AUTH_HEADER,
	}).then(r => r.json())

	const subFundGroupAccounts = yield Promise.all(subFundGroups.results.map(co.wrap(function * (group) {
		const accounts = yield fetch(`${BASE_URL}/acct?subFundGroupCode=${group.subFundGroupCode}`, {
			headers: AUTH_HEADER,
		}).then(r => r.json())
		return accounts
	})))

	const accounts = _.flatten(subFundGroupAccounts.map(accounts => accounts.results.map(account => ({
		chartOfAccountsCode: account.chartOfAccountsCode,
		accountNumber: account.accountNumber,
	}))))
	console.log(accounts)
}).catch(console.error)
