const express = require('express');
const router = express.Router();
const verifyToken = require('../middleware/verifyTokenMiddleware')
const getMenu = require('../controllers/messMenu/getMenu')
const updateMenu = require('../controllers/messMenu/updateMenu')

router.get('/getMessMenu', verifyToken, getMenu)
router.post('/updateMessMenu', verifyToken, updateMenu)

module.exports = router