const express = require('express')
const router = express.Router()
const verifyToken = require('../middleware/verifyTokenMiddleware')
const getStudent = require('../controllers/student/getStudent')
const getContractor = require('../controllers/contractor/getContractor')

router.get('/student', verifyToken, getStudent)
router.get('/contractor', verifyToken, getContractor)

module.exports = router