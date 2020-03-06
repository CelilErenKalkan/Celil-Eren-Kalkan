using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class Pengooen : MonoBehaviour
{

    private Animator anim;

    [Header("Booleans")]
    public bool isTouched;
    public bool isNavMeshed;
    public bool isSmart;
    public bool shouldFall;
    public bool isSwimming;
    public bool who;
    public bool follow;

    [Header("AI")]
    public NavMeshAgent Pingu;
    NavMeshHit closestHit;

    [Header("Mathematic")]
    public float pinguDistance = 5f;
    public int index;

    [Header("GameObjects")]
    public GameObject Player;
    public GameObject Penguin;
    public GameObject Opp;

    public Material playerColor;
    public Material oppColor;

    [Header("Positions")]
    public Vector3 toPos;
    public Vector3 oppToPos;
    public Vector3 fromPos;
    public Vector3 runDes;
    public Vector3 target;
    public Vector3 direction;
    private Vector3 motion;
    private Vector3 newPos = new Vector3(0, 0.5f, -30);
    public Positions positions;

    [Header("Angles")]
    public Quaternion fromAngle;
    public Quaternion toAngle;
    public Quaternion oppToAngle;
    public Quaternion rotateTowards;

    GameObject objectToFollow;
    private float _followSpeed = 28f;
    private float _followdistance = 1.3f;
    private float Distance;
    private float oppDistance;

    private int Touched = 13;
    private int OppTouched = 14;

    private int collisionGround = 0;

    private void Awake()
    {
        //anim = GetComponent<Animator>();
        //Prevents the NavMesh engine works before the object interact with plane.
        Pingu = GetComponent<NavMeshAgent>();
        Pingu.enabled = false;
        follow = true;
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Start is called before the first frame update
    void Start()
    {
        //////////////Getting beginning positions//////////////
        toAngle = gameObject.transform.rotation;
        oppToAngle = gameObject.transform.rotation;
        toPos = Player.transform.position;
        oppToPos = Opp.transform.position;


    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    // Update is called once per frame
    void Update()
    {

        Distance = Vector3.Distance(transform.position, Player.transform.position);

        oppDistance = Vector3.Distance(transform.position, Opp.transform.position);

        if (GameManager.Instance.isPlayable == true && isTouched == false && isSmart == true) //Running away.
        {
            if (Distance < pinguDistance) //From Player.
            {
                RunAway(Distance, pinguDistance);
            }

            if (oppDistance < pinguDistance) //From NPC.
            {
                RunAway(oppDistance, pinguDistance);
            }
        }

        if (GameManager.Instance.changeTouched && isTouched)
            LayerChanger();
    }

    private void FixedUpdate()
    {
        if (shouldFall && gameObject.CompareTag("Touched"))
            gameObject.transform.position = Vector3.Lerp(gameObject.transform.position, toPos, Time.fixedDeltaTime * 5);
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void OnTriggerEnter(Collider other) //Trigger the collider with Player interaction.
    {
        if (!isTouched)
        {
            if (other.gameObject.CompareTag("Collider")) //Trigger with the player.
            {
                Pingu.enabled = false;
                isSmart = false;
                isTouched = true;
                who = false;
                GameManager.Instance.score += 1;
                GameManager.Instance.total += 1;
                GameManager.Instance.UpdateScore();
                gameObject.layer = Touched;

                if (ToFollow.followList.Count == 0)
                {
                    gameObject.tag = "Touched";
                    gameObject.GetComponent<MeshRenderer>().material = playerColor;
                    transform.GetChild(0).gameObject.GetComponent<MeshRenderer>().material = playerColor;
                    transform.GetChild(1).gameObject.GetComponent<MeshRenderer>().material = playerColor;
                    objectToFollow = other.gameObject;
                }
                else
                {
                    Invoke("TagChanger", 1);
                    gameObject.GetComponent<MeshRenderer>().material = playerColor;
                    transform.GetChild(0).gameObject.GetComponent<MeshRenderer>().material = playerColor;
                    transform.GetChild(1).gameObject.GetComponent<MeshRenderer>().material = playerColor;
                    objectToFollow = ToFollow.followList[ToFollow.followList.Count - 1];
                }

                ToFollow.followList.Add(gameObject);

                StartCoroutine("Follow");
            }

            if (other.gameObject.CompareTag("Opp")) //Trigger with the opponent.
            {
                Pingu.enabled = false;
                isSmart = false;
                who = true;
                isTouched = true;
                Invoke("TagChanger", 1);
                gameObject.layer = OppTouched;
                gameObject.GetComponent<MeshRenderer>().material = oppColor;
                transform.GetChild(0).gameObject.GetComponent<MeshRenderer>().material = oppColor;
                transform.GetChild(1).gameObject.GetComponent<MeshRenderer>().material = oppColor;
                GameManager.Instance.total += 1;

                if (OppToFollow.oppFollowList.Count == 0)
                {
                    objectToFollow = other.gameObject;
                }
                else
                {
                    objectToFollow = OppToFollow.oppFollowList[OppToFollow.oppFollowList.Count - 1];
                }

                OppToFollow.oppFollowList.Add(gameObject);

                StartCoroutine(Follow());
            }

            if (other.gameObject.CompareTag("Hole")) //Lets the object fall from the hole.
            {
                Debug.Log("Kandırdım");
                shouldFall = true;
                toPos = other.gameObject.transform.position;
                StopFollow();
                Pingu.enabled = false;
                isSmart = false;
                gameObject.GetComponent<Rigidbody>().constraints = RigidbodyConstraints.None;
                gameObject.layer = 12;
            }

            if (other.gameObject.CompareTag("Faller") && isSwimming == false) //Destroys if the object has fallen.
            {
                isSwimming = true;
                Invoke("Swim", 1);
                GameManager.Instance.total += 1;
                GameManager.Instance.missedPenguin += 1;
                Invoke("Destroy", 3);
            }


        }
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void OnCollisionEnter(Collision collision) //Enables Nav Mesh to prevent collision bugs.
    {
        if (GameManager.Instance.isPlayable == true)
        {
            if (collision.gameObject.CompareTag("Ground") && collisionGround>=0)
            {
                if (shouldFall == false)
                {
                    collisionGround--;
                  
                    gameObject.GetComponent<Rigidbody>().constraints = RigidbodyConstraints.FreezePositionY | RigidbodyConstraints.FreezeRotationX | RigidbodyConstraints.FreezeRotationY | RigidbodyConstraints.FreezeRotationZ;
                    isNavMeshed = true;
                    Pingu.enabled = true;
                }
            }
        }
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void Destroy() //Destroys itself.
    {
        StopFollow();
        Destroy(gameObject);
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    void FaceTarget() //While running away, it looks the opposite side of the player.
    {
        Vector3 direction = (-Player.transform.position + transform.position).normalized;
        Quaternion lookRotation = Quaternion.LookRotation(new Vector3(direction.x, 0, direction.z));
        fromAngle = transform.rotation;
        transform.rotation = Quaternion.Slerp(fromAngle, lookRotation, Time.deltaTime * 5f);
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    void Swim() //Boolean for triggering with water.
    {
        isSwimming = false;
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    private void RunAway(float Distance, float pinguDistance)
        {
            if (Distance < pinguDistance) //From Player.
            {
                if (isNavMeshed && GameManager.Instance.hasFallen == false)
                {
                    Vector3 runaway = (transform.position - Player.transform.position) / 2;
                    Vector3 newPos = transform.position + runaway;
                    Pingu.SetDestination(newPos);
                    fromAngle = transform.rotation;
                }
            }
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    void TagChanger() //Changes its tag.
    {
        gameObject.tag = "Touched";
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    void LayerChanger()
    {
        Pingu.enabled = false;
        gameObject.layer = 12;
    }

    /// ////////////////////////////////////////////////////////////////////////////////////////////////////    /// ////////////////////////////////////////////////////////////////////////////////////////////////////

    IEnumerator Follow()
    {
        while (objectToFollow != null)
        {
            var tempPos = (transform.position - objectToFollow.transform.position).normalized;
            if (Distance > 1.3f)
            {
                transform.position = Vector3.MoveTowards(transform.position, objectToFollow.transform.position + _followdistance * tempPos, _followSpeed * Time.deltaTime);
                transform.rotation = Quaternion.Slerp(transform.rotation, objectToFollow.transform.rotation, Time.deltaTime * 15f);
            }

            yield return null;
        }
    }

    public void StopFollow()
    {
        LayerChanger();
        StopCoroutine("Follow");
        Debug.Log("Nazlı yarim");
    }
}