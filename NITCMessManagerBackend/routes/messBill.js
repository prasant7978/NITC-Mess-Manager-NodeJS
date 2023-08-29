const express = require('express')
const router = express.Router()

const verifyToken = require('../middleware/verifyTokenMiddleware')
const generateBill = require('../controllers/messBill/generateBill')
const getMessBill = require('../controllers/messBill/getMessBill')
const payBill = require('../controllers/messBill/payBill')

router.put('/generateBill', verifyToken, generateBill)
router.get('/getMessBill', verifyToken, getMessBill)
router.put('/payBill', verifyToken, payBill)

module.exports = router