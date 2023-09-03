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

const getAllStudents = require('../controllers/admin/getAllStudentsProfile')

const getStudentWithId = require('../controllers/admin/getStudentWithId')
const updateStudent = require('../controllers/admin/updateStudentProfile')
const deleteStudent = require('../controllers/admin/deleteStudent')
const addStudent = require('../controllers/admin/addStudent')

const getContractorWithId = require('../controllers/admin/getContractorWithId')
const adminUpdateContractorProfile = require('../controllers/admin/updateContractorProfile')
const deleteContractor = require('../controllers/admin/deleteContractor')
const addContractor = require('../controllers/admin/addContractor')

router.get('/student', verifyToken, getStudent)
router.get('/contractor', verifyToken, getContractor)
router.get('/admin', verifyToken, getAdmin)

router.put('/updateContractor', verifyToken, updateContractor)

router.get('/allContractor', verifyToken, getAllContractor)

router.post('/addMessNameToStudentProfile', verifyToken, addMessNameToStudent)

router.get('/allEnrolledStudent', verifyToken, getAllEnrolledStudents)

router.get('/allStudents', verifyToken, getAllStudents)

router.get('/studentWithId', verifyToken, getStudentWithId)
router.put('/updateStudent', verifyToken, updateStudent)
router.delete('/deleteStudent', verifyToken, deleteStudent)
router.post('/addStudent', verifyToken, addStudent)

router.get('/contractorWithId', verifyToken, getContractorWithId)
router.put('/adminUpdateContractor', verifyToken, adminUpdateContractorProfile)
router.delete('/deleteContractor', verifyToken, deleteContractor)
router.post('/addContractor', verifyToken, addContractor)

module.exports = router