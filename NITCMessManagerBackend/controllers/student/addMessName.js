const Student = require('../../models/student')
const Contractor = require('../../models/contractor')

module.exports = async(req, res) => {
    // try{

    var studentUpdated = Student.updateOne({_id: req.id},{
        $set: {
            "messEnrolled": req.query.messName
        }
    })

    studentUpdated.then((studentUpdated) => {
        console.log("mess added to student")
        var updatedContractor = Contractor.updateOne({messName: req.query.messName},
            {
                $push: {
                    "studentEnrolled": req.id
                },
                $inc: {
                    "availability": -1
                }
            }
        )

        updatedContractor.then((updatedContractor) => {
            console.log("student added to the mess")
            res.status(200).send(true)
        }).catch((err) => {
            console.log("student not added to the mess")
            console.log("Error: " + err);
            res.status(400).send(false)
        })

    }).catch((err) => {
        console.log("mess not added to student")
        console.log("Error: " + err);
        res.status(400).send(false)
    })

    // }
    // catch(err){
    //     console.log("Error: " + err.message)
    //     res.status(400).send(false);
    // }
}