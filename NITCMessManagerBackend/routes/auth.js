const express = require('express')
const router = express.Router()

const loginStudent = require('../controllers/student/loginStudent')
const loginContractor = require('../controllers/contractor/loginContractor')
const loginAdmin = require('../controllers/admin/loginAdmin')

const signupStudent = require('../controllers/student/signupStudent')

const verifyToken = require('../functions/verifyToken')

router.get('/verifyToken', verifyToken)

router.post('/signup/student', signupStudent)

router.post('/login/student', loginStudent)
router.post('/login/contractor', loginContractor)
router.post('/login/admin', loginAdmin)

module.exports = router