package nl.bos;

import com.cordys.notification.customdispatch.CustomTaskDispatcher;
import com.cordys.notification.customdispatch.TaskInformation;
import com.cordys.notification.internal.task.Assignment;
import com.cordys.notification.task.AssignmentType;
import com.cordys.notification.task.IAssignment;

import java.util.ArrayList;
import java.util.Collection;

public class MyCustomTaskDispatcher implements CustomTaskDispatcher {

    @Override
    public Collection<IAssignment> getAssignments(TaskInformation taskInformation) {
        // Write your logic here to decide on the target.
        String userDN = "";
        String RoleDN = "";
        String TeamID = "";
        String WorklistID = "";

        // Create Assignment object based on the target. Targets could be either a User, Role, Team, or a Work list.
        // If the target is a role or a user, provide the corresponding DN as the parameter.
        // If the target is a Team or a Work list then provide the ID of the team or work list as the parameter.
        IAssignment assignmentUser = new Assignment(userDN, AssignmentType.user);
        IAssignment assignmentRole = new Assignment(RoleDN, AssignmentType.role);
        IAssignment assignmentTeam = new Assignment(TeamID, AssignmentType.team);
        IAssignment assignmentWorklist = new Assignment(WorklistID, AssignmentType.worklist);

        ArrayList<IAssignment> assignments = new ArrayList<>();
        // Either of these the assignment objects or any combination of these assignment objects can be added to the returned assignments collection.
        assignments.add(assignmentUser);
        assignments.add(assignmentRole);
        assignments.add(assignmentWorklist);

        //UseCase # If any of the current target types is 'team', then only add the 'team Assignment' to the Assignments
        Collection<IAssignment> CurrentTargets = taskInformation.getAssignments();

        for (IAssignment assignment1 : CurrentTargets) {
            if (assignment1.getType() == AssignmentType.team) {
                assignments.add(assignmentTeam);
            }
        }
        return assignments;
    }
}
