const Contractor = require('../../models/contractor')
const Student = require('../../models/student')

module.exports = async(req, res) => {
    try{
        const contractor = await Contractor.findOne({_id: req.id}).exec()

        if(contractor){
            try{
                const students = await Student.find({messEnrolled: contractor.messName}).exec()
                console.log(students);

                if(students){
                    var countBillNotPaidStudent = 0
                    for(student of students) {
                        if(student.paymentStatus == "not-paid")
                            countBillNotPaidStudent++
                    }

                    console.log("Students not paid bill: " + countBillNotPaidStudent);

                    let costPerStudent = contractor.totalDue / countBillNotPaidStudent

                    res.status(200).send(JSON.stringify({
                        "totalDue": contractor.totalDue,
                        "noOfStudents": countBillNotPaidStudent,
                        "costPerStudent": costPerStudent
                    }))
                }
                else{
                    console.log("Student not found")
                    res.status(400).send(JSON.stringify(null))
                }
            }catch(err){
                console.log(err)
                res.status(400).send(JSON.stringify(null))
            }
        }
        else{
            console.log("Contractor not found")
            res.status(400).send(JSON.stringify(null))
        }
    }catch(err){
        console.log(err)
        res.status(400).send(JSON.stringify(null))
    }
}