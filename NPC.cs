using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

public class NPC : MonoBehaviour
{

    public NavMeshAgent Opp;

    public float range = 500.0f;
    public float radius = 500.0f;

    public GameObject target;
    public GameObject Wall;

    public Vector3 toPos;
    public Vector3 fromPos;
    public Vector3 runDes;
    public Vector3 startPos;
    private Vector3 newPos = new Vector3(0, 0.5f, 38f);

    public Quaternion fromAngle;
    public Quaternion toAngle;

    NavMeshHit closestHit;
    public Animator anim;
     

    // Start is called before the first frame update
    void Start()
    {
        anim = transform.GetChild(1).gameObject.GetComponent<Animator>();
        startPos = gameObject.transform.position;
        toAngle = gameObject.transform.rotation;
        Opp = GetComponent<NavMeshAgent>();

    }

    // Update is called once per frame
    void Update()
    {
        if(target == null || target.CompareTag("Touched")) //If there is no target, it call the function to select a new one.
        {
            Penguins(transform.position, radius);
        }

        if (GameManager.Instance.isPlayable == true && target != null && GameManager.Instance.total != 20) //Follows the target.
        {
            Follow();
        }
        else if (gameObject.GetComponent<NavMeshAgent>().enabled)
            oppReturn();

        if (GameManager.Instance.NPCreturn)
            gameObject.transform.position = Vector3.Lerp(transform.position, newPos, Time.fixedDeltaTime * 2);
    }
    
    void FaceTarget()
    {
        Vector3 direction = (target.transform.position - transform.position).normalized;
        Quaternion lookRotation = Quaternion.LookRotation(new Vector3(direction.x, 0, direction.z));
        fromAngle = transform.rotation;
        transform.rotation = Quaternion.Slerp(fromAngle, lookRotation, Time.deltaTime * 5f);
    }

    void Penguins(Vector3 center, float radius) //Selects a target to follow.
    {
        Collider[] detected = Physics.OverlapSphere(center, radius);
        for(int i = 0; i < detected.Length; i++)
        {
            if(detected[i].CompareTag("Penguin"))
            {
                target = detected[i].gameObject;
                toPos = target.transform.position;
            }
        }

    }

    void Follow()
    {
        anim.SetBool("isWalking", true);
        float Distance = Vector3.Distance(transform.position, target.transform.position);

        if (Distance < range)
        {
            Vector3 runaway = (transform.position - target.transform.position);
            Vector3 newPos = transform.position - runaway;
            Opp.SetDestination(newPos);
            fromAngle = transform.rotation;
        }
    }

    void Run()
    {
        float Distance = Vector3.Distance(transform.position, target.transform.position);

        if (Distance < range)
        {
            Vector3 runaway = (transform.position - target.transform.position);
            Vector3 newPos = transform.position + runaway;
            Opp.SetDestination(newPos);
            fromAngle = transform.rotation;
        }
    }

    private void OnTriggerEnter(Collider other)
    {
        if(other.CompareTag("Penguin"))
        {
            Penguins(transform.position, radius);
        }

        if (other.gameObject.CompareTag("OppCollector"))
        {
            Debug.Log("Deneme");
            Wall.GetComponent<Collider>().enabled = false;
            gameObject.GetComponent<NavMeshAgent>().enabled = false;
            transform.GetChild(1).gameObject.SetActive(false);
            GameManager.Instance.NPCreturn = true;
            gameObject.GetComponent<Collider>().isTrigger = false;

        }
    }

    void oppReturn()
    {
        anim.SetBool("isWalking", true);
        Opp.SetDestination(startPos);
    }
}
