export class NewIssue {
    public id: string;
    public projectId: string;
    public issueStatus: string;
    public issueTypeId: string;
    public issueType: string;
    public title: string;
    public labelNames: string[];
    public versionNames: string[];
    public env: string;
    public description: string;
    public rank: string;
    public ownerId: string;
    public ownerName: string;
    public reporterId: string;
    public reporterName: string;
	public issueLink: string;
	public createdAt: string;

    constructor(
        projectId: string,
        issueStatus: string,
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
        this.issueStatus = issueStatus;
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