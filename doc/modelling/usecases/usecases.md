# Use-case for the different user classes


## Special terms


### Node

To enable flexibility with the organization of courses, we have a hierarchy of something we simply call *Nodes*
above *courses* in our hierarchy. Each node can have a parent-node above itself in the hierarchy, and either one or
more child-nodes or a course below itself in the hierarchy. This makes is possible to create hierarchies like this:

* University in Oslo
    * Faculty of Mathemathics and Natural Sciences
        * Department of Informatics
        * Department of Physics
        * Department of Mathematics
    * Faculty of Law
        * Department of Private Law
        * Department of Public and International Law


### Delivery-candidate

Students have a maximum of one *delivery* on each assignment. A delivery consists of one or more *delivery-candidates*.
Delivery candidates are timestamped. The *corrector* will normally correct the last submitted delivery-candidate, and
if the student need to correct their initial delivery, they will continue submitting delivery-candidates on the same
delivery. 



## Preconditions

The user has to authenticate with the JavaEE server. Context determines if the **role** of a user: *student*,
*corrector* or *administrator*. A user might have different roles in different parts of the system. They might, for
example, be *student* on one course, while they are *corrector* on another course.