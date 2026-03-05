const jwt = require('jsonwebtoken');
const User = require('../models/User');

const authMiddleware = (req, res, next) => {
    const token = req.header('Authorization');

    if (!token) {
        return res.status(401).json({ message: 'Access denied. No token provided.' });
    }

    try {
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        req.user = decoded;
        next();
    } catch (ex) {
        res.status(400).json({ message: 'Invalid token.' });
    }
};

const isAdmin = (req, res, next) => {
    User.findById(req.user.id)
        .then(user => {
            if (!user || !user.isAdmin) {
                return res.status(403).json({ message: 'Access denied. Admins only.' });
            }
            next();
        })
        .catch(err => res.status(500).json({ message: 'Server error.' }));
};

module.exports = {
    authMiddleware,
    isAdmin
};