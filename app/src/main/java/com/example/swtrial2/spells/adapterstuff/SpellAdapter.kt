package com.example.swtrial2.spells.adapterstuff

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.buildSpannedString
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.swtrial2.R


class SpellAdapter(private val mycontext: Context,private val dataset: MutableList<String>,private val bigdataset: List<String>,private val favlist: MutableList<String>) : RecyclerView.Adapter<ViewHolder>() {
    val levels: List<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    class EmptySpellHolder(view: View) : ViewHolder(view){
        private val emptyrelout: RelativeLayout
        init{
            emptyrelout= view.findViewById(R.id.emptyrelout)
        }
    }
    class LeveledDividerHolder(view: View) : ViewHolder(view){
        val lvldividertextview: TextView
        init {
            lvldividertextview= view.findViewById(R.id.leveldividertextview)
        }
    }
    class SpellHolder(view: View) : ViewHolder(view){
        val spellname: TextView
        val spelldetails: TextView
        val castingtime: TextView
        val constlout: ConstraintLayout
        val imbutton: ImageButton

        init {

            imbutton = view.findViewById(R.id.table_spell_fav)
            constlout = view.findViewById(R.id.table_spell_constlout)
            spellname= view.findViewById(R.id.table_spell_name)
            castingtime= view.findViewById(R.id.table_spell_casting_time)
            spelldetails= view.findViewById(R.id.table_spell_details)
        }

    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view: View
        return when(viewType){
            0->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spell_button, viewGroup, false) ; SpellHolder(view) }
            1->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spell_button_big, viewGroup, false) ; SpellHolder(view) }
            2->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.empty_button50sp, viewGroup, false) ; EmptySpellHolder(view) }
            3->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.spell_leveldivider, viewGroup, false) ; LeveledDividerHolder(view)
            }
            else->{view = LayoutInflater.from(viewGroup.context).inflate(R.layout.empty_button50sp, viewGroup, false) ; EmptySpellHolder(view)
            }
        }



    }
    override fun getItemViewType(position: Int): Int {
        return when(dataset[position]){
            in bigdataset -> {1}
            "empty" ->{2}
            in levels.toString() ->{3}
            else ->{0}
        }

    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        when(viewHolder.itemViewType){
            2->{}
            3->{setlevel(viewHolder as LeveledDividerHolder,dataset[position].toInt())}
            else->{spell(viewHolder as SpellHolder,dataset[position])}
        }


    }
    fun setSpellList(updatedspelllist: List<String>){
        val diffResult = DiffUtil.calculateDiff(EquipmentDiffUtilCallback(dataset,updatedspelllist))
        dataset.clear()
        dataset.addAll(updatedspelllist)
        diffResult.dispatchUpdatesTo(this)

    }
    override fun getItemCount() = dataset.size
    private fun updatefav(name: String, spellbutton: View){
        if(name !in favlist){
            favlist.add(name)
            spellbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
        }
        else{
            favlist.remove(name)
            spellbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
        }
        with(mycontext.getSharedPreferences("favlist",Context.MODE_PRIVATE).edit()){
            putStringSet("favlist",favlist.toMutableSet())
            apply()
        }
    }





    private fun textchange(view: SpellHolder, text: CharSequence, action: CharSequence, spell: String, name: String){
        view.spellname.text = text
        view.castingtime.text = action
        if(spell.isBlank()){
            view.constlout.setOnClickListener{
                Toast.makeText(mycontext, "Unknown Spell:$name",Toast.LENGTH_LONG)
                    .show()
            }
        }
        else{
            view.constlout.setOnClickListener{
                mycontext.startActivity(Intent(mycontext, Class.forName("com.example.swtrial2.spells.$spell")))
            }
            view.imbutton.setOnClickListener {
                updatefav(name,view.imbutton)
            }
            if(name in favlist){
                view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
            }
            else{
                view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
            }
            /*view.imbutton.contentDescription=name*/
        }
    }


    private fun spelltest(view: SpellHolder, name: String){
        when (name) {
            "affect mind" -> {textchange(view,mycontext.getText(R.string.affect_mind_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Affect_mind",name)}
            "affliction" -> {textchange(view,mycontext.getText(R.string.affliction_button_table),mycontext.getText(R.string.ActionCon),"Affliction",name)}
            "animate weapon" -> {textchange(view,mycontext.getText(R.string.animate_weapon_button_table),mycontext.getText(R.string.BonusAction),"Animate_weapon",name)}
            "armor of abeloth" -> {textchange(view,mycontext.getText(R.string.armor_of_abeloth_button_table),mycontext.getText(R.string.Action),"Armor_of_abeloth",name)}
            "aura of purity" -> {textchange(view,mycontext.getText(R.string.aura_of_purity_button_table),mycontext.getText(R.string.ActionCon),"Aura_of_purity",name)}
            "aura of vigor" -> {textchange(view,mycontext.getText(R.string.aura_of_vigor_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Aura_of_vigor",name)}
            "battle insight" -> {textchange(view,mycontext.getText(R.string.battle_insight_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Battle_insight",name)}
            "battle meditation" -> {textchange(view,mycontext.getText(R.string.battle_meditation_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Battle_meditation",name)}
            "battle precognition" -> {textchange(view,mycontext.getText(R.string.battle_precognition_button_table),mycontext.getText(
                R.string.Action
            ),"Battle_precognition",name)}
            "beacon of hope" -> {textchange(view,mycontext.getText(R.string.beacon_of_hope_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Beacon_of_hope",name)}
            "beast trick" -> {textchange(view,mycontext.getText(R.string.beast_trick_button_table),mycontext.getText(
                R.string.Action
            ),"Beast_trick",name)}
            "bestow curse" -> {textchange(view,mycontext.getText(R.string.bestow_curse_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Bestow_curse",name)}
            "breath control" -> {textchange(view,mycontext.getText(R.string.breath_control_button_table),mycontext.getText(
                R.string.Action
            ),"Breath_control",name)}
            "burst" -> {textchange(view,mycontext.getText(R.string.burst_button_table),mycontext.getText(
                R.string.Action
            ),"Burst",name)}
            "burst of speed" -> {textchange(view,mycontext.getText(R.string.burst_of_speed_button_table),mycontext.getText(
                R.string.Action
            ),"Burst_of_speed",name)}
            "call lightning" -> {textchange(view,mycontext.getText(R.string.call_lightning_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Call_lightning",name)}
            "calm emotions" -> {textchange(view,mycontext.getText(R.string.calm_emotions_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Calm_emotions",name)}
            "choke" -> {textchange(view,mycontext.getText(R.string.choke_button_table),mycontext.getText(
                R.string.Action
            ),"Choke",name)}
            "cloud mind" -> {textchange(view,mycontext.getText(R.string.cloud_mind_button_table),mycontext.getText(
                R.string.Action
            ),"Cloud_mind",name)}
            "coerce mind" -> {textchange(view,mycontext.getText(R.string.coerce_mind_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Coerce_mind",name)}
            "comprehend speech" -> {textchange(view,mycontext.getText(R.string.comprehend_speech_button_table),mycontext.getText(
                R.string.Action
            ),"Comprehend_speech",name)}
            "control pain" -> {textchange(view,mycontext.getText(R.string.control_pain_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Control_pain",name)}
            "convulsion" -> {textchange(view,mycontext.getText(R.string.convulsion_button_table),mycontext.getText(
                R.string.Action
            ),"Convulsion",name)}
            "crush" -> {textchange(view,mycontext.getText(R.string.crush_button_table),mycontext.getText(
                R.string.Action
            ),"Crush",name)}
            "curse" -> {textchange(view,mycontext.getText(R.string.curse_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Curse",name)}
            "danger sense" -> {textchange(view,mycontext.getText(R.string.danger_sense_button_table),mycontext.getText(
                R.string.Action
            ),"Danger_sense",name)}
            "dark aura" -> {textchange(view,mycontext.getText(R.string.dark_aura_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Dark_aura",name)}
            "dark shear" -> {textchange(view,mycontext.getText(R.string.dark_shear_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Dark_shear",name)}
            "dark side tendrils" -> {textchange(view,mycontext.getText(R.string.dark_side_tendrils_button_table),mycontext.getText(
                R.string.Action
            ),"Dark_side_tendrils",name)}
            "darkness" -> {textchange(view,mycontext.getText(R.string.darkness_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Darkness",name)}
            "death field" -> {textchange(view,mycontext.getText(R.string.death_field_button_table),mycontext.getText(
                R.string.Action
            ),"Death_field",name)}
            "denounce" -> {textchange(view,mycontext.getText(R.string.denounce_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Denounce",name)}
            "destroy droid" -> {textchange(view,mycontext.getText(R.string.destroy_droid_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Destroy_droid",name)}
            "disable droid" -> {textchange(view,mycontext.getText(R.string.disable_droid_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Disable_droid",name)}
            "disperse force" -> {textchange(view,mycontext.getText(R.string.disperse_force_button_table),mycontext.getText(
                R.string.Reaction
            ),"Disperse_force",name)}
            "dominate beast" -> {textchange(view,mycontext.getText(R.string.dominate_beast_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Dominate_beast",name)}
            "dominate mind" -> {textchange(view,mycontext.getText(R.string.dominate_mind_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Dominate_mind",name)}
            "dominate monster" -> {textchange(view,mycontext.getText(R.string.dominate_monster_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Dominate_monster",name)}
            "drain life" -> {textchange(view,mycontext.getText(R.string.drain_life_button_table),mycontext.getText(
                R.string.Action
            ),"Drain_life",name)}
            "drain vitality" -> {textchange(view,mycontext.getText(R.string.drain_vitality_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Drain_vitality",name)}
            "dun moch" -> {textchange(view,mycontext.getText(R.string.dun_moch_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Dun_moch",name)}
            "earthquake" -> {textchange(view,mycontext.getText(R.string.earthquake_button_table),mycontext.getText(
                R.string.Action
            ),"Earthquake",name)}
            "enfeeble" -> {textchange(view,mycontext.getText(R.string.enfeeble_button_table),mycontext.getText(
                R.string.Action
            ),"Enfeeble",name)}
            "eruption" -> {textchange(view,mycontext.getText(R.string.eruption_button_table),mycontext.getText(
                R.string.Action
            ),"Eruption",name)}
            "fear" -> {textchange(view,mycontext.getText(R.string.fear_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Fear",name)}
            "feedback" -> {textchange(view,mycontext.getText(R.string.feedback_button_table),mycontext.getText(
                R.string.Action
            ),"Feedback",name)}
            "force barrier" -> {textchange(view,mycontext.getText(R.string.force_barrier_button_table),mycontext.getText(
                R.string.Action
            ),"Force_barrier",name)}
            "force blind deafen" -> {textchange(view,mycontext.getText(R.string.force_blind_deafen_button_table),mycontext.getText(
                R.string.Action
            ),"Force_blind_deafen",name)}
            "force blinding" -> {textchange(view,mycontext.getText(R.string.force_blinding_button_table),mycontext.getText(
                R.string.Action
            ),"Force_blinding",name)}
            "force blur" -> {textchange(view,mycontext.getText(R.string.force_blur_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_blur",name)}
            "force body" -> {textchange(view,mycontext.getText(R.string.force_body_button_table),mycontext.getText(
                R.string.Action
            ),"Force_body",name)}
            "force breach" -> {textchange(view,mycontext.getText(R.string.force_breach_button_table),mycontext.getText(
                R.string.Action
            ),"Force_breach",name)}
            "force camouflage" -> {textchange(view,mycontext.getText(R.string.force_camouflage_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_camouflage",name)}
            "force chain lightning" -> {textchange(view,mycontext.getText(R.string.force_chain_lightning_button_table),mycontext.getText(
                R.string.Action
            ),"Force_chain_lightning",name)}
            "force concealment" -> {textchange(view,mycontext.getText(R.string.force_concealment_button_table),mycontext.getText(
                R.string.Action
            ),"Force_concealment",name)}
            "force confusion" -> {textchange(view,mycontext.getText(R.string.force_confusion_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_confusion",name)}
            "force disarm" -> {textchange(view,mycontext.getText(R.string.force_disarm_button_table),mycontext.getText(
                R.string.Action
            ),"Force_disarm",name)}
            "force enlightenment" -> {textchange(view,mycontext.getText(R.string.force_enlightenment_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_enlightenment",name)}
            "force focus" -> {textchange(view,mycontext.getText(R.string.force_focus_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Force_focus",name)}
            "force imbuement" -> {textchange(view,mycontext.getText(R.string.force_imbuement_button_table),mycontext.getText(
                R.string.BonusAction
            ),"Force_imbuement",name)}
            "force immunity" -> {textchange(view,mycontext.getText(R.string.force_immunity_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_immunity",name)}
            "force jump" -> {textchange(view,mycontext.getText(R.string.force_jump_button_table),mycontext.getText(
                R.string.Action
            ),"Force_jump",name)}
            "force leap" -> {textchange(view,mycontext.getText(R.string.force_leap_button_table),mycontext.getText(
                R.string.BonusAction
            ),"Force_leap",name)}
            "force lightning" -> {textchange(view,mycontext.getText(R.string.force_lightning_button_table),mycontext.getText(
                R.string.Action
            ),"Force_lightning",name)}
            "force lightning cone" -> {textchange(view,mycontext.getText(R.string.force_lightning_cone_button_table),mycontext.getText(
                R.string.Action
            ),"Force_lightning_cone",name)}
            "force link" -> {textchange(view,mycontext.getText(R.string.force_link_button_table),mycontext.getText(
                R.string.Action
            ),"Force_link",name)}
            "force mask" -> {textchange(view,mycontext.getText(R.string.force_mask_button_table),mycontext.getText(
                R.string.Action
            ),"Force_mask",name)}
            "force meld" -> {textchange(view,mycontext.getText(R.string.force_meld_button_table),mycontext.getText(
                R.string.Action
            ),"Force_meld",name)}
            "force mend" -> {textchange(view,mycontext.getText(R.string.force_mend_button_table),mycontext.getText(
                R.string.OneMinute
            ),"Force_mend",name)}
            "force project" -> {textchange(view,mycontext.getText(R.string.force_project_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_project",name)}
            "force propel" -> {textchange(view,mycontext.getText(R.string.force_propel_button_table),mycontext.getText(
                R.string.Action
            ),"Force_propel",name)}
            "force push pull" -> {textchange(view,mycontext.getText(R.string.force_push_pull_button_table),mycontext.getText(
                R.string.Action
            ),"Force_push_pull",name)}
            "force reflect" -> {textchange(view,mycontext.getText(R.string.force_reflect_button_table),mycontext.getText(
                R.string.Reaction
            ),"Force_reflect",name)}
            "force repulse" -> {textchange(view,mycontext.getText(R.string.force_repulse_button_table),mycontext.getText(
                R.string.Action
            ),"Force_repulse",name)}
            "force scream" -> {textchange(view,mycontext.getText(R.string.force_scream_button_table),mycontext.getText(
                R.string.Action
            ),"Force_scream",name)}
            "force shunt" -> {textchange(view,mycontext.getText(R.string.force_shunt_button_table),mycontext.getText(
                R.string.Action
            ),"Force_shunt",name)}
            "force sight" -> {textchange(view,mycontext.getText(R.string.force_sight_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_sight",name)}
            "force storm" -> {textchange(view,mycontext.getText(R.string.force_storm_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Force_storm",name)}
            "force suppression" -> {textchange(view,mycontext.getText(R.string.force_suppression_button_table),mycontext.getText(
                R.string.Action
            ),"Force_suppression",name)}
            "force technique" -> {textchange(view,mycontext.getText(R.string.force_technique_button_table),mycontext.getText(
                R.string.Action
            ),"Force_technique",name)}
            "force throw" -> {textchange(view,mycontext.getText(R.string.force_throw_button_table),mycontext.getText(
                R.string.Action
            ),"Force_throw",name)}
            "force trance" -> {textchange(view,mycontext.getText(R.string.force_trance_button_table),mycontext.getText(
                R.string.Action
            ),"Force_trance",name)}
            "force vision" -> {textchange(view,mycontext.getText(R.string.force_vision_button_table),mycontext.getText(
                R.string.OneMinute
            ),"Force_vision",name)}
            "force weapon" -> {textchange(view,mycontext.getText(R.string.force_weapon_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Force_weapon",name)}
            "force whisper" -> {textchange(view,mycontext.getText(R.string.force_whisper_button_table),mycontext.getText(
                R.string.Action
            ),"Force_whisper",name)}
            "freedom of movement" -> {textchange(view,mycontext.getText(R.string.freedom_of_movement_button_table),mycontext.getText(
                R.string.Action
            ),"Freedom_of_movement",name)}
            "give life" -> {textchange(view,mycontext.getText(R.string.give_life_button_table),mycontext.getText(
                R.string.Action
            ),"Give_life",name)}
            "grasping vine" -> {textchange(view,mycontext.getText(R.string.grasping_vine_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Grasping_vine",name)}
            "greater feedback" -> {textchange(view,mycontext.getText(R.string.greater_feedback_button_table),mycontext.getText(
                R.string.Action
            ),"Greater_feedback",name)}
            "greater heal" -> {textchange(view,mycontext.getText(R.string.greater_heal_button_table),mycontext.getText(
                R.string.Action
            ),"Greater_heal",name)}
            "greater saber throw" -> {textchange(view,mycontext.getText(R.string.greater_saber_throw_button_table),mycontext.getText(
                R.string.Action
            ),"Greater_saber_throw",name)}
            "guidance" -> {textchange(view,mycontext.getText(R.string.guidance_button_table),mycontext.getText(
                R.string.Action
            ),"Guidance",name)}
            "hallucination" -> {textchange(view,mycontext.getText(R.string.hallucination_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Hallucination",name)}
            "heal" -> {textchange(view,mycontext.getText(R.string.heal_button_table),mycontext.getText(
                R.string.Action
            ),"Heal",name)}
            "heroism" -> {textchange(view,mycontext.getText(R.string.heroism_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Heroism",name)}
            "hex" -> {textchange(view,mycontext.getText(R.string.hex_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Hex",name)}
            "horror" -> {textchange(view,mycontext.getText(R.string.horror_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Horror",name)}
            "hysteria" -> {textchange(view,mycontext.getText(R.string.hysteria_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Hysteria",name)}
            "improved battle meditation" -> {textchange(view,mycontext.getText(R.string.improved_battle_meditation_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Improved_battle_meditation",name)}
            "improved dark side tendrils" -> {textchange(view,mycontext.getText(R.string.improved_dark_side_tendrils_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Improved_dark_side_tendrils",name)}
            "improved feedback" -> {textchange(view,mycontext.getText(R.string.improved_feedback_button_table),mycontext.getText(
                R.string.Action
            ),"Improved_feedback",name)}
            "improved force barrier" -> {textchange(view,mycontext.getText(R.string.improved_force_barrier_button_table),mycontext.getText(
                R.string.TenMinutes
            ),"Improved_force_barrier",name)}
            "improved force camouflage" -> {textchange(view,mycontext.getText(R.string.improved_force_camouflage_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Improved_force_camouflage",name)}
            "improved force immunity" -> {textchange(view,mycontext.getText(R.string.improved_force_immunity_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Improved_force_immunity",name)}
            "improved force scream" -> {textchange(view,mycontext.getText(R.string.improved_force_scream_button_table),mycontext.getText(
                R.string.Action
            ),"Improved_force_scream",name)}
            "improved heal" -> {textchange(view,mycontext.getText(R.string.improved_heal_button_table),mycontext.getText(
                R.string.Action
            ),"Improved_heal",name)}
            "improved phasestrike" -> {textchange(view,mycontext.getText(R.string.improved_phasestrike_button_table),mycontext.getText(
                R.string.Action
            ),"Improved_phasestrike",name)}
            "improved phasewalk" -> {textchange(view,mycontext.getText(R.string.improved_phasewalk_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Improved_phasewalk",name)}
            "improved restoration" -> {textchange(view,mycontext.getText(R.string.improved_restoration_button_table),mycontext.getText(
                R.string.Action
            ),"Improved_restoration",name)}
            "improved revitalize" -> {textchange(view,mycontext.getText(R.string.improved_revitalize_button_table),mycontext.getText(
                R.string.TenMinutes
            ),"Improved_revitalize",name)}
            "improved saber throw" -> {textchange(view,mycontext.getText(R.string.improved_saber_throw_button_table),mycontext.getText(
                R.string.Action
            ),"Improved_saber_throw",name)}
            "insanity" -> {textchange(view,mycontext.getText(R.string.insanity_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Insanity",name)}
            "kill" -> {textchange(view,mycontext.getText(R.string.kill_button_table),mycontext.getText(
                R.string.Action
            ),"Kill",name)}
            "knight speed" -> {textchange(view,mycontext.getText(R.string.knight_speed_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Knight_speed",name)}
            "lightning charge" -> {textchange(view,mycontext.getText(R.string.lightning_charge_button_table),mycontext.getText(
                R.string.Action
            ),"Lightning_charge",name)}
            "locate creature" -> {textchange(view,mycontext.getText(R.string.locate_creature_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Locate_creature",name)}
            "locate object" -> {textchange(view,mycontext.getText(R.string.locate_object_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Locate_object",name)}
            "maddening darkness" -> {textchange(view,mycontext.getText(R.string.maddening_darkness_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Maddening_darkness",name)}
            "malacia" -> {textchange(view,mycontext.getText(R.string.malacia_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Malacia",name)}
            "mass animation" -> {textchange(view,mycontext.getText(R.string.mass_animation_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mass_animation",name)}
            "mass coerce mind" -> {textchange(view,mycontext.getText(R.string.mass_coerce_mind_button_table),mycontext.getText(
                R.string.Action
            ),"Mass_coerce_mind",name)}
            "mass hysteria" -> {textchange(view,mycontext.getText(R.string.mass_hysteria_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mass_hysteria",name)}
            "mass malacia" -> {textchange(view,mycontext.getText(R.string.mass_malacia_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mass_malacia",name)}
            "master battle meditation" -> {textchange(view,mycontext.getText(R.string.master_battle_meditation_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Master_battle_meditation",name)}
            "master feedback" -> {textchange(view,mycontext.getText(R.string.master_feedback_button_table),mycontext.getText(
                R.string.Action
            ),"Master_feedback",name)}
            "master force barrier" -> {textchange(view,mycontext.getText(R.string.master_force_barrier_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Master_force_barrier",name)}
            "master force immunity" -> {textchange(view,mycontext.getText(R.string.master_force_immunity_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Master_force_immunity",name)}
            "master force scream" -> {textchange(view,mycontext.getText(R.string.master_force_scream_button_table),mycontext.getText(
                R.string.Action
            ),"Master_force_scream",name)}
            "master heal" -> {textchange(view,mycontext.getText(R.string.master_heal_button_table),mycontext.getText(
                R.string.Action
            ),"Master_heal",name)}
            "master malacia" -> {textchange(view,mycontext.getText(R.string.master_malacia_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Master_malacia",name)}
            "master revitalize" -> {textchange(view,mycontext.getText(R.string.master_revitalize_button_table),mycontext.getText(
                R.string.OneHour
            ),"Master_revitalize",name)}
            "master saber throw" -> {textchange(view,mycontext.getText(R.string.master_saber_throw_button_table),mycontext.getText(
                R.string.Action
            ),"Master_saber_throw",name)}
            "master speed" -> {textchange(view,mycontext.getText(R.string.master_speed_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Master_speed",name)}
            "mind blank" -> {textchange(view,mycontext.getText(R.string.mind_blank_button_table),mycontext.getText(
                R.string.Action
            ),"Mind_blank",name)}
            "mind prison" -> {textchange(view,mycontext.getText(R.string.mind_prison_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mind_prison",name)}
            "mind spike" -> {textchange(view,mycontext.getText(R.string.mind_spike_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mind_spike",name)}
            "mind trap" -> {textchange(view,mycontext.getText(R.string.mind_trap_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mind_trap",name)}
            "mind trick" -> {textchange(view,mycontext.getText(R.string.mind_trick_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Mind_trick",name)}
            "morichro" -> {textchange(view,mycontext.getText(R.string.morichro_button_table),mycontext.getText(
                R.string.Action
            ),"Morichro",name)}
            "necrotic charge" -> {textchange(view,mycontext.getText(R.string.necrotic_charge_button_table),mycontext.getText(
                R.string.Action
            ),"Necrotic_charge",name)}
            "necrotic touch" -> {textchange(view,mycontext.getText(R.string.necrotic_touch_button_table),mycontext.getText(
                R.string.Action
            ),"Necrotic_touch",name)}
            "phasestrike" -> {textchange(view,mycontext.getText(R.string.phasestrike_button_table),mycontext.getText(
                R.string.BonusActionCon
            ),"Phasestrike",name)}
            "phasewalk" -> {textchange(view,mycontext.getText(R.string.phasewalk_button_table),mycontext.getText(
                R.string.BonusAction
            ),"Phasewalk",name)}
            "plague" -> {textchange(view,mycontext.getText(R.string.plague_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Plague",name)}
            "plant surge" -> {textchange(view,mycontext.getText(R.string.plant_surge_button_table),mycontext.getText(
                R.string.Actionor8hours
            ),"Plant_surge",name)}
            "precognition" -> {textchange(view,mycontext.getText(R.string.precognition_button_table),mycontext.getText(
                R.string.OneMinute
            ),"Precognition",name)}
            "project" -> {textchange(view,mycontext.getText(R.string.project_button_table),mycontext.getText(
                R.string.Action
            ),"Project",name)}
            "psychic charge" -> {textchange(view,mycontext.getText(R.string.psychic_charge_button_table),mycontext.getText(
                R.string.Action
            ),"Psychic_charge",name)}
            "psychometry" -> {textchange(view,mycontext.getText(R.string.psychometry_button_table),mycontext.getText(
                R.string.OneMinute
            ),"Psychometry",name)}
            "rage" -> {textchange(view,mycontext.getText(R.string.rage_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Rage",name)}
            "rebuke" -> {textchange(view,mycontext.getText(R.string.rebuke_button_table),mycontext.getText(
                R.string.Action
            ),"Rebuke",name)}
            "remove curse" -> {textchange(view,mycontext.getText(R.string.remove_curse_button_table),mycontext.getText(
                R.string.Action
            ),"Remove_curse",name)}
            "rescue" -> {textchange(view,mycontext.getText(R.string.rescue_button_table),mycontext.getText(
                R.string.BonusAction
            ),"Rescue",name)}
            "resistance" -> {textchange(view,mycontext.getText(R.string.resistance_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Resistance",name)}
            "restoration" -> {textchange(view,mycontext.getText(R.string.restoration_button_table),mycontext.getText(
                R.string.Action
            ),"Restoration",name)}
            "revitalize" -> {textchange(view,mycontext.getText(R.string.revitalize_button_table),mycontext.getText(
                R.string.OneMinute
            ),"Revitalize",name)}
            "ruin" -> {textchange(view,mycontext.getText(R.string.ruin_button_table),mycontext.getText(
                R.string.Action
            ),"Ruin",name)}
            "saber reflect" -> {textchange(view,mycontext.getText(R.string.saber_reflect_button_table),mycontext.getText(
                R.string.Reaction
            ),"Saber_reflect",name)}
            "saber throw" -> {textchange(view,mycontext.getText(R.string.saber_throw_button_table),mycontext.getText(
                R.string.Action
            ),"Saber_throw",name)}
            "saber ward" -> {textchange(view,mycontext.getText(R.string.saber_ward_button_table),mycontext.getText(
                R.string.Action
            ),"Saber_ward",name)}
            "sanctuary" -> {textchange(view,mycontext.getText(R.string.sanctuary_button_table),mycontext.getText(
                R.string.BonusAction
            ),"Sanctuary",name)}
            "sap vitality" -> {textchange(view,mycontext.getText(R.string.sap_vitality_button_table),mycontext.getText(
                R.string.Action
            ),"Sap_vitality",name)}
            "scourge" -> {textchange(view,mycontext.getText(R.string.scourge_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Scourge",name)}
            "seethe" -> {textchange(view,mycontext.getText(R.string.seethe_button_table),mycontext.getText(
                R.string.Action
            ),"Seethe",name)}
            "sense emotion" -> {textchange(view,mycontext.getText(R.string.sense_emotion_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Sense_emotion",name)}
            "sense force" -> {textchange(view,mycontext.getText(R.string.sense_force_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Sense_force",name)}
            "sever force" -> {textchange(view,mycontext.getText(R.string.sever_force_button_table),mycontext.getText(
                R.string.Reaction
            ),"Sever_force",name)}
            "shadow sight" -> {textchange(view,mycontext.getText(R.string.shadow_sight_button_table),mycontext.getText(
                R.string.Action
            ),"Shadow_sight",name)}
            "share life" -> {textchange(view,mycontext.getText(R.string.share_life_button_table),mycontext.getText(
                R.string.Action
            ),"Share_life",name)}
            "shock" -> {textchange(view,mycontext.getText(R.string.shock_button_table),mycontext.getText(
                R.string.Action
            ),"Shock",name)}
            "shocking shield" -> {textchange(view,mycontext.getText(R.string.shocking_shield_button_table),mycontext.getText(
                R.string.Action
            ),"Shocking_shield",name)}
            "shroud of darkness" -> {textchange(view,mycontext.getText(R.string.shroud_of_darkness_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Shroud_of_darkness",name)}
            "siphon life" -> {textchange(view,mycontext.getText(R.string.siphon_life_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Siphon_life",name)}
            "skill empowerment" -> {textchange(view,mycontext.getText(R.string.skill_empowerment_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Skill_empowerment",name)}
            "slow" -> {textchange(view,mycontext.getText(R.string.slow_button_table),mycontext.getText(
                R.string.Action
            ),"Slow",name)}
            "slow descent" -> {textchange(view,mycontext.getText(R.string.slow_descent_button_table),mycontext.getText(
                R.string.Reaction
            ),"Slow_descent",name)}
            "sonic charge" -> {textchange(view,mycontext.getText(R.string.sonic_charge_button_table),mycontext.getText(
                R.string.Action
            ),"Sonic_charge",name)}
            "sound trick" -> {textchange(view,mycontext.getText(R.string.sound_trick_button_table),mycontext.getText(
                R.string.Action
            ),"Sound_trick",name)}
            "spare the dying" -> {textchange(view,mycontext.getText(R.string.spare_the_dying_button_table),mycontext.getText(
                R.string.Action
            ),"Spare_the_dying",name)}
            "spirit blade" -> {textchange(view,mycontext.getText(R.string.spirit_blade_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Spirit_blade",name)}
            "stasis" -> {textchange(view,mycontext.getText(R.string.stasis_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Stasis",name)}
            "stasis field" -> {textchange(view,mycontext.getText(R.string.stasis_field_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Stasis_field",name)}
            "stun" -> {textchange(view,mycontext.getText(R.string.stun_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Stun",name)}
            "stun droid" -> {textchange(view,mycontext.getText(R.string.stun_droid_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Stun_droid",name)}
            "sustained lightning" -> {textchange(view,mycontext.getText(R.string.sustained_lightning_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Sustained_lightning",name)}
            "telekinesis" -> {textchange(view,mycontext.getText(R.string.telekinesis_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Telekinesis",name)}
            "telekinetic burst" -> {textchange(view,mycontext.getText(R.string.telekinetic_burst_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Telekinetic_burst",name)}
            "telekinetic shield" -> {textchange(view,mycontext.getText(R.string.telekinetic_shield_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Telekinetic_shield",name)}
            "telekinetic storm" -> {textchange(view,mycontext.getText(R.string.telekinetic_storm_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Telekinetic_storm",name)}
            "telekinetic wave" -> {textchange(view,mycontext.getText(R.string.telekinetic_wave_button_table),mycontext.getText(
                R.string.Action
            ),"Telekinetic_wave",name)}
            "telemetry" -> {textchange(view,mycontext.getText(R.string.telemetry_button_table),mycontext.getText(
                R.string.OneMinute
            ),"Telemetry",name)}
            "tremor" -> {textchange(view,mycontext.getText(R.string.tremor_button_table),mycontext.getText(
                R.string.Action
            ),"Tremor",name)}
            "true sight" -> {textchange(view,mycontext.getText(R.string.true_sight_button_table),mycontext.getText(
                R.string.Action
            ),"True_sight",name)}
            "turbulence" -> {textchange(view,mycontext.getText(R.string.turbulence_button_table),mycontext.getText(
                R.string.Action
            ),"Turbulence",name)}
            "valor" -> {textchange(view,mycontext.getText(R.string.valor_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Valor",name)}
            "wall of light" -> {textchange(view,mycontext.getText(R.string.wall_of_light_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Wall_of_light",name)}
            "whirlwind" -> {textchange(view,mycontext.getText(R.string.whirlwind_button_table),mycontext.getText(
                R.string.ActionCon
            ),"Whirlwind",name)}
            "will of the force" -> {textchange(view,mycontext.getText(R.string.will_of_the_force_button_table),mycontext.getText(
                R.string.Action
            ),"Will_of_the_force",name)}
            "wound" -> {textchange(view,mycontext.getText(R.string.wound_button_table),mycontext.getText(
                R.string.Action
            ),"Wound",name)}
            "wrack" -> {textchange(view,mycontext.getText(R.string.wrack_button_table),mycontext.getText(
                R.string.Action
            ),"Wrack",name)}
            else -> {textchange(view,"Unknown Spell","","",name)
            }
        }
    }
    @SuppressLint("DiscouragedApi")
    fun spell(view: SpellHolder, name: String){
        val identifiq=mycontext.resources.getIdentifier(name,"array",mycontext.packageName)
        if(identifiq!=0){
            val templist = mycontext.resources.getTextArray(identifiq)
            view.spellname.text=templist[0]
            view.spelldetails.text= buildSpannedString{append(templist[2]);append(" ");append(templist[3])}
            view.castingtime.text= templist[1]
            view.constlout.setOnClickListener{
                mycontext.startActivity(Intent(mycontext, SpellDetailsActivity::class.java).putExtra(
                    SpellDetailsActivity.Spell_Name,name))
            }
            view.imbutton.setOnClickListener {
                updatefav(name,view.imbutton)
            }
            if(name in favlist){
                view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegoldtrue)
            }
            else{
                view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
            }
        }
        else{
            view.spellname.text=mycontext.getString(R.string.eqerror404)
            view.spelldetails.text=""
            view.castingtime.text =""
            view.imbutton.background=AppCompatResources.getDrawable(mycontext,R.drawable.favouritegold)
            view.imbutton.setOnClickListener{}
            view.constlout.setOnClickListener{}
        }
    }
    private fun setlevel(view: LeveledDividerHolder, lvl: Int){
        when(lvl){
            0->{view.lvldividertextview.text=mycontext.getText(R.string.at_will)}
            1->{view.lvldividertextview.text=mycontext.getText(R.string.first_level)}
            2->{view.lvldividertextview.text=mycontext.getText(R.string.second_level)}
            3->{view.lvldividertextview.text=mycontext.getText(R.string.third_level)}
            4->{view.lvldividertextview.text=mycontext.getText(R.string.fourth_level)}
            5->{view.lvldividertextview.text=mycontext.getText(R.string.fifth_level)}
            6->{view.lvldividertextview.text=mycontext.getText(R.string.sixth_level)}
            7->{view.lvldividertextview.text=mycontext.getText(R.string.seventh_level)}
            8->{view.lvldividertextview.text=mycontext.getText(R.string.eighth_level)}
            9->{view.lvldividertextview.text=mycontext.getText(R.string.nineth_level)}
            else->{

            }
        }
    }
}