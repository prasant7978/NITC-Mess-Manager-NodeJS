const express = require('express');
const router = express.Router();
const verifyToken = require('../middleware/verifyTokenMiddleware')
const addFeedback = require('../controllers/feedback/addFeedback')

router.put('/addFeedback', verifyToken, addFeedback)

module.exports = router