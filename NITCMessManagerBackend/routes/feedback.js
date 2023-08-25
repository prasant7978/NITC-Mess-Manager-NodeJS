const express = require('express');
const router = express.Router();
const verifyToken = require('../middleware/verifyTokenMiddleware')
const addFeedback = require('../controllers/feedback/addFeedback')
const getAllFeedbacks = require('../controllers/feedback/getAllFeedbacks')
const deleteFeedback = require('../controllers/feedback/deleteFeedback')

router.put('/addFeedback', verifyToken, addFeedback)
router.get('/getAllFeedbacks', verifyToken, getAllFeedbacks)
router.delete('/deleteFeedback', verifyToken, deleteFeedback)

module.exports = router