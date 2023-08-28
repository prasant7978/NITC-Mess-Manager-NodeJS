const express = require('express')
const router = express.Router()

const verifyToken = require('../middleware/verifyTokenMiddleware')
const generateBill = require('../controllers/messBill/generateBill')
const getMessBill = require('../controllers/messBill/getMessBill')

router.put('/generateBill', verifyToken, generateBill)
router.get('/getMessBill', verifyToken, getMessBill)

module.exports = router