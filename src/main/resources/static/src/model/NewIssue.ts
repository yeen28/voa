export class NewIssue {
    private projectId: string;
    private issueTypeId: string;
    private title: string;
    private labelNames: string[];
    private versionNames: string[];
    private env: string;
    private description: string;
    private rank: string;
    private ownerId: string;
    private reporterId: string;
	private issueLink: string;

    constructor(
        projectId: string,
        issueTypeId: string,
        title: string,
        labelNames: string[],
        versionNames: string[],
        env: string,
        description: string,
        rank: string,
        ownerId: string,
        reporterId: string,
        issueLink: string
    ) {
        this.projectId = projectId;
        this.issueTypeId = issueTypeId;
        this.title = title;
        this.labelNames = labelNames;
        this.versionNames = versionNames;
        this.env = env;
        this.description = description;
        this.rank = rank;
        this.ownerId = ownerId;
        this.reporterId = reporterId;
        this.issueLink = issueLink;
    }
}