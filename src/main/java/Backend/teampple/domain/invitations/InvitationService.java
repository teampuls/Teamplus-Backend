package Backend.teampple.domain.invitations;

import Backend.teampple.domain.invitations.dto.response.GetInvitationDto;
import Backend.teampple.domain.invitations.dto.response.GetInvitationValidationDto;
import Backend.teampple.domain.invitations.entity.Invitation;
import Backend.teampple.domain.invitations.repository.InvitationRepository;
import Backend.teampple.domain.tasks.entity.Task;
import Backend.teampple.domain.teams.entity.Team;
import Backend.teampple.domain.teams.entity.Teammate;
import Backend.teampple.domain.teams.repository.TeammateRepository;
import Backend.teampple.domain.teams.repository.TeamsRepository;
import Backend.teampple.global.error.ErrorCode;
import Backend.teampple.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class InvitationService {

    private final TeamsRepository teamsRepository;

    private final InvitationRepository invitationRepository;

    private final TeammateRepository teammateRepository;

    public GetInvitationDto getInvitation(Long teamId) {
        // 1. 팀 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. 코드 생성
        String code = UUID.randomUUID().toString() // 10자리
                .replaceAll("-","")
                .substring(3,13);

        // 3. 레디스용 객체(invitation) 생성
        Invitation invitation = Invitation.builder()
                .teamId(teamId)
                .code(code)
                .build();

        // 4. 레디스 저장
        invitationRepository.save(invitation);

        // 5. 주소로 변환
        String link = "www.teampple.com/login/" + code;

        // 6. return
        return GetInvitationDto.builder()
                .url(link)
                .build();
    }

    public GetInvitationValidationDto getInvitationValidation(String code) {
        // 1. 초대장 찾기
        Invitation invite = invitationRepository.findByCode(code);

        // 2.1 초대장 없으면 return
        if (invite == null) {
            return GetInvitationValidationDto.builder()
                    .teamName("")
                    .isValid(false)
                    .build();
        }

        // 2.2 초대장 존재하면 팀 조회 후 return
        Team team = teamsRepository.findById(invite.getTeamId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));
        return GetInvitationValidationDto.builder()
                .teamName(team.getName())
                .isValid(true)
                .build();
    }

    public void postInvitation(Long teamId) {
        // 1. 팀 조회
        Team team = teamsRepository.findById(teamId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.TASK_NOT_FOUND.getMessage()));

        // 2. 팀원 생성
//        Teammate teammate = Teammate.builder()
//                .user()
//                .userProfile()
//                .team(team)
//                .build();
//        teammateRepository.save(teammate);
    }
}
