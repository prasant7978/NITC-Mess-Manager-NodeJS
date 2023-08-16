const express = require('express')
const router = express.Router()
const verifyToken = require('../middleware/verifyTokenMiddleware')
const getStudent = require('../controllers/student/getStudent')
const getContractor = require('../controllers/contractor/getContractor')
const getAdmin = require('../controllers/admin/getAdmin')

router.get('/student', verifyToken, getStudent)
router.get('/contractor', verifyToken, getContractor)
router.get('/admin', verifyToken, getAdmin)

module.exports = router