const express = require('express')
const router = express.Router()
const verifyToken = require('../middleware/verifyTokenMiddleware')
const getStudent = require('../controllers/student/getStudent')

router.get('/student', verifyToken, getStudent)

module.exports = router