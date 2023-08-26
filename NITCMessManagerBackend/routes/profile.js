const express = require('express')
const router = express.Router()

const verifyToken = require('../middleware/verifyTokenMiddleware')
const getStudent = require('../controllers/student/getStudent')
const getContractor = require('../controllers/contractor/getContractorProfile')
const getAdmin = require('../controllers/admin/getAdmin')
const updateContractor = require('../controllers/contractor/updateContractor')
const getAllContractor = require('../controllers/contractor/getAllContractorProfile')
const addMessNameToStudent = require('../controllers/student/addMessName')
const getAllEnrolledStudents = require('../controllers/contractor/getAllEnrolledStudent')

router.get('/student', verifyToken, getStudent)
router.get('/contractor', verifyToken, getContractor)
router.get('/admin', verifyToken, getAdmin)
router.put('/updateContractor', verifyToken, updateContractor)
router.get('/allContractor', verifyToken, getAllContractor)
router.post('/addMessNameToStudentProfile', verifyToken, addMessNameToStudent)
router.get('/allEnrolledStudent', verifyToken, getAllEnrolledStudents)

module.exports = router